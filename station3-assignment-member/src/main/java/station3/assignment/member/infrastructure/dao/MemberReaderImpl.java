package station3.assignment.member.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.Member;
import station3.assignment.member.domain.service.MemberReader;
import station3.assignment.member.domain.service.dto.MemberDTO;
import station3.assignment.member.infrastructure.exception.status.AlreadyDataException;
import station3.assignment.member.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.member.infrastructure.exception.status.NotFoundDataException;
import station3.assignment.member.infrastructure.exception.status.UnauthorizedException;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이미 존재하는 회원인지 확인
     * @param command: 회원 정보
     * @return Void: 조회된 회원 없음
     */
    @Override
    public Mono<Void> memberExistCheck(MemberCommand.MemberRegister command) {

        return memberRepository.findByMemberLoginIdAndMemberType(command.getMemberLoginId(), command.getMemberType())
            .hasElement()
            .flatMap(alreadyMember -> {
                if (alreadyMember) return Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataMember.getMessage()));

                return Mono.empty();
            });
    }

    /**
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    @Override
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {

        return memberRepository.findByMemberToken(memberToken)
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundMember.getMessage())))
            .flatMap(member -> Mono.just(new MemberDTO.MemberIdInfo(member.getMemberId())));
    }

    /**
     * 로그인 검증
     * @param command: 로그인 정보
     * @return Member: 회원 레퍼런스
     */
    @Override
    public Mono<Member> loginVerify(MemberCommand.MemberLogin command) {

        // 로그안 이아디로 회원 조회
        Mono<Member> memberMono = memberRepository.findByMemberLoginId(command.getMemberLoginId());

        return memberMono
            .switchIfEmpty(Mono.error(new UnauthorizedException(ExceptionMessage.InvalidMemberLogin.getMessage())))
            .flatMap(member -> {
                // 비밀번호 검증
                if (!passwordEncoder.matches(command.getMemberPassword(), member.getMemberPassword()))
                    return Mono.error(new UnauthorizedException(ExceptionMessage.InvalidMemberLogin.getMessage()));

                return Mono.just(member);
            });
    }
}
