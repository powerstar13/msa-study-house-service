package station3.assignment.member.infrastructure.factory;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.Member;
import station3.assignment.member.domain.MemberType;
import station3.assignment.member.domain.service.dto.MemberDTO;
import station3.assignment.member.presentation.request.MemberRegisterRequest;
import station3.assignment.member.presentation.response.MemberRegisterResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MemberTestFactory {

    private static final MemberType memberType = MemberType.LESSOR;
    private static final String memberLoginId = "test@gmail.com";
    private static final String memberPassword = "111111";
    private static final String encodedMemberPassword = "$2a$10$oKmFs6UNvZhEoKhJYI5rxOmNU6/c8oUpkVYry.PBKcY1ZuHMToppa";
    private static final String memberName = "이름";
    private static final LocalDate memberBirth = LocalDate.of(1991, 1, 1);
    private static final String memberPhone = "01012341234";
    private static final String memberToken = UUID.randomUUID().toString();

    public static String getEncodedMemberPassword() {
        return encodedMemberPassword;
    }

    /**
     * 회원 등록 정보
     */
    public static MemberCommand.MemberRegister memberRegisterCommand() {

        return MemberCommand.MemberRegister.builder()
            .memberType(memberType)
            .memberLoginId(memberLoginId)
            .memberPassword(memberPassword)
            .memberName(memberName)
            .memberBirth(memberBirth)
            .memberPhone(memberPhone)
            .build();
    }

    /**
     * 회원 정보
     */
    public static Member member() {

        return Member.builder()
            .memberId(1)
            .memberToken(memberToken)
            .memberType(memberType)
            .memberLoginId(memberLoginId)
            .memberPassword(encodedMemberPassword)
            .memberName(memberName)
            .memberBirth(memberBirth)
            .memberPhone(memberPhone)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Mono<Member> memberMono() {
        return Mono.just(member());
    }

    /**
     * 회원 대체 식별키 정보
     */
    public static MemberDTO.MemberTokenInfo memberTokenInfo() {
        return MemberDTO.MemberTokenInfo.builder()
            .memberToken(memberToken)
            .build();
    }

    public static Mono<MemberDTO.MemberTokenInfo> memberTokenInfoMono() {
        return Mono.just(memberTokenInfo());
    }

    /**
     * 회원 가입 Request
     */
    public static MemberRegisterRequest memberRegisterRequest() {
        return MemberRegisterRequest.builder()
            .memberType(memberType)
            .memberLoginId(memberLoginId)
            .memberPassword(memberPassword)
            .memberName(memberName)
            .memberBirth(memberBirth)
            .memberPhone(memberPhone)
            .build();
    }

    /**
     * 회원 가입 Response
     */
    public static MemberRegisterResponse memberRegisterResponse() {
        return MemberRegisterResponse.builder()
            .memberToken(memberToken)
            .accessToken("accessToken")
            .refreshToken("refreshToken")
            .build();
    }
}
