package msa.study.member.domain.service;

import msa.study.member.application.dto.MemberCommand;
import msa.study.member.infrastructure.exception.status.AlreadyDataException;
import msa.study.member.infrastructure.exception.status.ExceptionMessage;
import msa.study.member.infrastructure.factory.MemberTestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import msa.study.member.domain.Member;
import msa.study.member.domain.MemberType;
import msa.study.member.domain.service.dto.MemberDTO;
import msa.study.member.infrastructure.dao.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MemberReaderTest {

    @Autowired
    private MemberReader memberReader;

    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @DisplayName("이미 존재하는 회원인지 확인")
    @Test
    void memberExistCheck() {

        MemberCommand.MemberRegister command = MemberTestFactory.memberRegisterCommand();

        given(memberRepository.findByMemberLoginIdAndMemberType(anyString(), any(MemberType.class))).willReturn(Mono.empty());

        Mono<Void> result = memberReader.memberExistCheck(command);

        verify(memberRepository).findByMemberLoginIdAndMemberType(anyString(), any(MemberType.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("이미 존재하는 회원인지 확인 > 이미 존재함")
    @Test
    void memberExistCheckException() {

        MemberCommand.MemberRegister command = MemberTestFactory.memberRegisterCommand();

        given(memberRepository.findByMemberLoginIdAndMemberType(anyString(), any(MemberType.class)))
            .willReturn(Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataMember.getMessage())));

        Mono<Void> result = memberReader.memberExistCheck(command);

        verify(memberRepository).findByMemberLoginIdAndMemberType(anyString(), any(MemberType.class));

        StepVerifier.create(result.log())
            .expectError(AlreadyDataException.class)
            .verify();
    }

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {

        given(memberRepository.findByMemberToken(anyString())).willReturn(MemberTestFactory.memberMono());

        Mono<MemberDTO.MemberIdInfo> result = memberReader.exchangeMemberToken("memberToken");

        verify(memberRepository).findByMemberToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(memberIdInfo -> assertTrue(memberIdInfo.getMemberId() > 0))
            .verifyComplete();
    }

    @DisplayName("로그인 검증")
    @Test
    void loginVerify() {
        final String memberLoginId = "아이디";
        final String memberPassword = "비밀번호";

        MemberCommand.MemberLogin command = MemberCommand.MemberLogin.builder()
            .memberLoginId(memberLoginId)
            .memberPassword(memberPassword)
            .build();

        given(memberRepository.findByMemberLoginId(anyString())).willReturn(MemberTestFactory.memberMono());
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        Mono<Member> result = memberReader.loginVerify(command);

        verify(memberRepository).findByMemberLoginId(anyString());

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }
}