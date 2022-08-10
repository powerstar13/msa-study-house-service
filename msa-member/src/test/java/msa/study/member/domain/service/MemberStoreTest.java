package msa.study.member.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import msa.study.member.application.dto.MemberCommand;
import msa.study.member.application.dto.MemberCommandMapper;
import msa.study.member.domain.Member;
import msa.study.member.domain.MemberType;
import msa.study.member.infrastructure.dao.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static msa.study.member.infrastructure.factory.MemberTestFactory.*;

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

        given(passwordEncoder.encode(anyString())).willReturn(getEncodedMemberPassword());
        given(memberCommandMapper.of(any(MemberCommand.MemberRegister.class))).willReturn(member());
        given(memberRepository.save(any(Member.class))).willReturn(memberMono());

        Mono<Member> result = memberStore.memberRegister(command);

        verify(passwordEncoder).encode(anyString());
        verify(memberCommandMapper).of(any(MemberCommand.MemberRegister.class));
        verify(memberRepository).save(any(Member.class));

        StepVerifier.create(result.log())
            .assertNext(member -> assertAll(() -> {
                assertInstanceOf(MemberType.class, member.getMemberType());
                assertNotNull(member.getMemberToken());
            }))
            .verifyComplete();
    }
}