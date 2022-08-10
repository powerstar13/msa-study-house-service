package msa.study.member.application;

import msa.study.member.domain.service.MemberService;
import msa.study.member.infrastructure.factory.MemberTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import msa.study.member.application.dto.MemberCommand;
import msa.study.member.domain.service.dto.MemberDTO;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MemberFacadeTest {

    @Autowired
    private MemberFacade memberFacade;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원 등록")
    @Test
    void memberRegister() {
        MemberCommand.MemberRegister command = MemberTestFactory.memberRegisterCommand();

        given(memberService.memberRegister(any(MemberCommand.MemberRegister.class))).willReturn(MemberTestFactory.memberTokenInfoMono());

        Mono<MemberDTO.MemberTokenInfo> result = memberFacade.memberRegister(command);

        verify(memberService).memberRegister(any(MemberCommand.MemberRegister.class));

        StepVerifier.create(result.log())
            .assertNext(memberTokenInfo -> assertAll(() -> assertNotNull(memberTokenInfo)))
            .verifyComplete();
    }

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {

        given(memberService.exchangeMemberToken(anyString())).willReturn(MemberTestFactory.memberIdInfoMono());

        Mono<MemberDTO.MemberIdInfo> result = memberFacade.exchangeMemberToken(UUID.randomUUID().toString());

        verify(memberService).exchangeMemberToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(memberIdInfo -> assertTrue(memberIdInfo.getMemberId() > 0))
            .verifyComplete();
    }

    @DisplayName("회원 로그인 서비스")
    @Test
    void login() {

        given(memberService.login(any(MemberCommand.MemberLogin.class))).willReturn(MemberTestFactory.memberLoginInfoResponse());

        Mono<MemberDTO.MemberLoginInfo> result = memberFacade.login(MemberTestFactory.memberLoginCommand());

        verify(memberService).login(any(MemberCommand.MemberLogin.class));

        StepVerifier.create(result.log()).assertNext(memberLoginInfo -> assertAll(() -> {
                assertNotNull(memberLoginInfo);
                assertNotNull(memberLoginInfo.getMemberToken());
                assertNotNull(memberLoginInfo.getAccessToken());
                assertNotNull(memberLoginInfo.getRefreshToken());
            }))
            .verifyComplete();
    }
}