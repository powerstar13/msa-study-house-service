package station3.assignment.member.application.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.Member;
import station3.assignment.member.domain.member.service.MemberService;
import station3.assignment.member.domain.member.service.dto.MemberDTO;

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
}