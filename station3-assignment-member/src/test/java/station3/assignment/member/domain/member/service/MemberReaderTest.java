package station3.assignment.member.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.MemberType;
import station3.assignment.member.infrastructure.dao.member.MemberRepository;
import station3.assignment.member.infrastructure.exception.status.AlreadyDataException;
import station3.assignment.member.infrastructure.exception.status.ExceptionMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.member.infrastructure.factory.MemberTestFactory.memberRegisterCommand;

@SpringBootTest
class MemberReaderTest {

    @Autowired
    private MemberReader memberReader;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("이미 존재하는 회원인지 확인")
    @Test
    void memberExistCheck() {

        MemberCommand.MemberRegister command = memberRegisterCommand();

        given(memberRepository.findByMemberLoginIdAndMemberType(any(String.class), any(MemberType.class))).willReturn(Mono.empty());

        Mono<Void> voidMono = memberReader.memberExistCheck(command);

        verify(memberRepository).findByMemberLoginIdAndMemberType(any(String.class), any(MemberType.class));

        StepVerifier.create(voidMono.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("이미 존재하는 회원인지 확인 > 이미 존재함")
    @Test
    void memberExistCheckException() {

        MemberCommand.MemberRegister command = memberRegisterCommand();

        given(memberRepository.findByMemberLoginIdAndMemberType(any(String.class), any(MemberType.class)))
            .willReturn(Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataMember.getMessage())));

        Mono<Void> voidMono = memberReader.memberExistCheck(command);

        verify(memberRepository).findByMemberLoginIdAndMemberType(any(String.class), any(MemberType.class));

        StepVerifier.create(voidMono.log())
            .expectError(AlreadyDataException.class)
            .verify();
    }
}