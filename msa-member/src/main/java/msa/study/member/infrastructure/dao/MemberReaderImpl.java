package msa.study.member.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import msa.study.member.application.dto.MemberCommand;
import msa.study.member.domain.Member;
import msa.study.member.domain.service.MemberReader;
import msa.study.member.domain.service.dto.MemberDTO;
import msa.study.member.infrastructure.exception.status.AlreadyDataException;
import msa.study.member.infrastructure.exception.status.ExceptionMessage;
import msa.study.member.infrastructure.exception.status.NotFoundDataException;
import msa.study.member.infrastructure.exception.status.UnauthorizedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
            .flatMap(alreadyMember ->
                alreadyMember ?
                    Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataMember.getMessage()))
                    : Mono.empty()
            );
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
            .map(member -> new MemberDTO.MemberIdInfo(member.getMemberId()));
    }

    /**
     * 로그인 검증
     * @param command: 로그인 정보
     * @return Member: 회원 레퍼런스
     */
    @Override
    public Mono<Member> loginVerify(MemberCommand.MemberLogin command) {

        // 로그안 이아디로 회원 조회
        return memberRepository.findByMemberLoginId(command.getMemberLoginId())
            .switchIfEmpty(Mono.error(new UnauthorizedException(ExceptionMessage.InvalidMemberLogin.getMessage())))
            .map(member -> {
                // 비밀번호 검증
                if (!passwordEncoder.matches(command.getMemberPassword(), member.getMemberPassword()))
                    throw new UnauthorizedException(ExceptionMessage.InvalidMemberLogin.getMessage());

                return member;
            });
    }
}
