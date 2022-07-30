package station3.assignment.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.member.application.MemberFacade;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.service.MemberService;
import station3.assignment.member.domain.service.dto.MemberDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.member.infrastructure.factory.MemberTestFactory.*;

@SpringBootTest
class MemberFacadeTest {

    @Autowired
    private MemberFacade memberFacade;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원 등록")
    @Test
    void memberRegister() {
        MemberCommand.MemberRegister command = memberRegisterCommand();

        given(memberService.memberRegister(any(MemberCommand.MemberRegister.class))).willReturn(memberTokenInfoMono());

        Mono<MemberDTO.MemberTokenInfo> memberTokenInfoMono = memberFacade.memberRegister(command);

        verify(memberService).memberRegister(any(MemberCommand.MemberRegister.class));

        StepVerifier.create(memberTokenInfoMono.log())
            .assertNext(memberTokenInfo -> assertAll(() -> assertNotNull(memberTokenInfo)))
            .verifyComplete();
    }

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {

        given(memberService.exchangeMemberToken(any(String.class))).willReturn(memberIdInfoMono());

        Mono<MemberDTO.MemberIdInfo> memberIdInfoMono = memberFacade.exchangeMemberToken("memberToken");

        verify(memberService).exchangeMemberToken(any(String.class));

        StepVerifier.create(memberIdInfoMono.log())
            .assertNext(memberIdInfo -> assertTrue(memberIdInfo.getMemberId() > 0))
            .verifyComplete();
    }
}