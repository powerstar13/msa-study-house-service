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
import station3.assignment.member.domain.service.MemberReader;
import station3.assignment.member.domain.service.MemberService;
import station3.assignment.member.domain.service.MemberStore;
import station3.assignment.member.domain.service.dto.MemberDTO;
import station3.assignment.member.domain.service.dto.MemberDTOMapper;
import station3.assignment.member.infrastructure.jwt.factory.TokenStore;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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

        Mono<MemberDTO.MemberTokenInfo> memberTokenInfoMono = memberService.memberRegister(command);

        verify(memberReader).memberExistCheck(any(MemberCommand.MemberRegister.class));
        verify(memberStore).memberRegister(any(MemberCommand.MemberRegister.class));

        StepVerifier.create(memberTokenInfoMono.log())
            .assertNext(memberTokenInfo -> assertAll(() -> {
                assertNotNull(memberTokenInfo.getMemberToken());
                assertNotNull(memberTokenInfo.getAccessToken());
                assertNotNull(memberTokenInfo.getRefreshToken());
            }))
            .verifyComplete();
    }
}