package station3.assignment.member.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.application.dto.MemberCommandMapper;
import station3.assignment.member.domain.Member;
import station3.assignment.member.domain.service.MemberStore;
import station3.assignment.member.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.member.infrastructure.exception.status.RegisterFailException;

@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

    private final MemberRepository memberRepository;
    private final MemberCommandMapper memberCommandMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 등록
     * @param command: 회원 등록 정보
     * @return Member: 회원 레퍼런스
     */
    @Override
    public Mono<Member> memberRegister(MemberCommand.MemberRegister command) {

        String encodedMemberPassword = passwordEncoder.encode(command.getMemberPassword()); // 비밀번호 암호화

        Member member = memberCommandMapper.of(command);
        member.encodedMemberPasswordRegister(encodedMemberPassword); // 암호화된 비밀번호 등록
        member.memberTokenRegister(); // 회원 대체 식별키 등록

        return memberRepository.save(member)
            .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailMember.getMessage())));
    }
}
