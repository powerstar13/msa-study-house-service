package station3.assignment.member.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.Member;
import station3.assignment.member.domain.service.dto.MemberDTO;
import station3.assignment.member.domain.service.dto.MemberDTOMapper;
import station3.assignment.member.infrastructure.jwt.factory.TokenStore;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.member.infrastructure.factory.MemberTestFactory.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberReader memberReader;
    @MockBean
    private MemberStore memberStore;
    @MockBean
    private MemberDTOMapper memberDTOMapper;
    @MockBean
    private TokenStore tokenStore;

    @DisplayName("회원 가입")
    @Test
    void memberRegister() {
        MemberCommand.MemberRegister command = memberRegisterCommand();

        given(memberReader.memberExistCheck(any(MemberCommand.MemberRegister.class))).willReturn(Mono.empty());
        given(memberStore.memberRegister(any(MemberCommand.MemberRegister.class))).willReturn(memberMono());
        given(tokenStore.tokenPublish(any(String.class), any(Boolean.class))).willReturn("accessToken");
        given(tokenStore.tokenPublish(any(String.class), any(Boolean.class))).willReturn("refreshToken");
        given(memberDTOMapper.of(any(Member.class))).willReturn(memberTokenInfo());

        Mono<MemberDTO.MemberTokenInfo> result = memberService.memberRegister(command);

        verify(memberReader).memberExistCheck(any(MemberCommand.MemberRegister.class));
        verify(memberStore).memberRegister(any(MemberCommand.MemberRegister.class));

        StepVerifier.create(result.log())
            .assertNext(memberTokenInfo -> assertAll(() -> {
                assertNotNull(memberTokenInfo.getMemberToken());
                assertNotNull(memberTokenInfo.getAccessToken());
                assertNotNull(memberTokenInfo.getRefreshToken());
            }))
            .verifyComplete();
    }

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {

        given(memberReader.exchangeMemberToken(any(String.class))).willReturn(memberIdInfoMono());

        Mono<MemberDTO.MemberIdInfo> result = memberService.exchangeMemberToken(UUID.randomUUID().toString());

        verify(memberReader).exchangeMemberToken(any(String.class));

        StepVerifier.create(result.log())
            .assertNext(memberIdInfo -> assertTrue(memberIdInfo.getMemberId() > 0))
            .verifyComplete();
    }

    @DisplayName("회원 로그인 처리")
    @Test
    void login() {
        final String memberLoginId = "아이디"; // 교수 계정: 카이아이
        final String memberPassword = "비밀번호"; // 초기 비밀번호: 생년월일 6자리

        MemberCommand.MemberLogin command = MemberCommand.MemberLogin.builder()
            .memberLoginId(memberLoginId)
            .memberPassword(memberPassword)
            .build();

        // given
        given(memberReader.loginVerify(any(MemberCommand.MemberLogin.class))).willReturn(memberMono());
        given(tokenStore.tokenPublish(any(String.class), any(boolean.class))).willReturn("accessToken");
        given(tokenStore.tokenPublish(any(String.class), any(boolean.class))).willReturn("refreshToken");
        given(memberDTOMapper.of(any(Member.class), any(String.class), any(String.class))).willReturn(memberLoginInfo());

        Mono<MemberDTO.MemberLoginInfo> result = memberService.login(command);

        StepVerifier.create(result.log())
            .assertNext(memberLoginInfo -> assertAll(() -> {
                assertNotNull(memberLoginInfo);
                assertNotNull(memberLoginInfo.getMemberToken());
                assertNotNull(memberLoginInfo.getAccessToken());
                assertNotNull(memberLoginInfo.getRefreshToken());
            }))
            .verifyComplete();
    }
}