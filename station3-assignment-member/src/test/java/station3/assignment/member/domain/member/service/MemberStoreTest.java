package station3.assignment.member.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.application.member.dto.MemberCommandMapper;
import station3.assignment.member.domain.member.Member;
import station3.assignment.member.domain.member.MemberType;
import station3.assignment.member.infrastructure.dao.member.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.member.infrastructure.factory.MemberTestFactory.*;

@SpringBootTest
class MemberStoreTest {

    @Autowired
    private MemberStore memberStore;

    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private MemberCommandMapper memberCommandMapper;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 등록")
    @Test
    void memberRegister() {
        MemberCommand.MemberRegister command = memberRegisterCommand();

        given(passwordEncoder.encode(any(String.class))).willReturn(getEncodedMemberPassword());
        given(memberCommandMapper.of(any(MemberCommand.MemberRegister.class))).willReturn(member());
        given(memberRepository.save(any(Member.class))).willReturn(memberMono());

        Mono<Member> memberMono = memberStore.memberRegister(command);

        verify(passwordEncoder).encode(any(String.class));
        verify(memberCommandMapper).of(any(MemberCommand.MemberRegister.class));
        verify(memberRepository).save(any(Member.class));

        StepVerifier.create(memberMono.log())
            .assertNext(member -> assertAll(() -> {
                assertInstanceOf(MemberType.class, member.getMemberType());
                assertNotNull(member.getMemberToken());
            }))
            .verifyComplete();
    }
}