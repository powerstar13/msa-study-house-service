package station3.assignment.member.infrastructure.factory;

import org.apache.commons.lang3.RandomUtils;
import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.Member;
import station3.assignment.member.domain.MemberType;
import station3.assignment.member.domain.service.dto.MemberDTO;
import station3.assignment.member.presentation.request.MemberLoginRequest;
import station3.assignment.member.presentation.request.MemberRegisterRequest;
import station3.assignment.member.presentation.response.ExchangeMemberTokenResponse;
import station3.assignment.member.presentation.response.MemberLoginResponse;
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
            .memberId(RandomUtils.nextInt())
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

    /**
     * 회원 고유번호 정보
     */
    public static MemberDTO.MemberIdInfo memberIdInfo() {

        return MemberDTO.MemberIdInfo.builder()
            .memberId(RandomUtils.nextInt())
            .build();
    }

    public static Mono<MemberDTO.MemberIdInfo> memberIdInfoMono() {
        return Mono.just(memberIdInfo());
    }

    public static ExchangeMemberTokenResponse exchangeMemberTokenResponse() {

        return ExchangeMemberTokenResponse.builder()
            .memberId(RandomUtils.nextInt())
            .build();
    }

    /**
     * 로그인 회원 정보 구성
     */
    public static MemberDTO.MemberLoginInfo memberLoginInfo() {
        return MemberDTO.MemberLoginInfo.builder()
            .memberToken(UUID.randomUUID().toString())
            .accessToken("accessToken")
            .refreshToken("refreshToken")
            .memberName("이름")
            .build();
    }

    public static MemberCommand.MemberLogin memberLoginCommand() {
        return MemberCommand.MemberLogin.builder()
            .memberLoginId("아이디")
            .memberPassword("비밀번호")
            .build();
    }

    /**
     * 회원 로그인 Response 구성
     */
    public static Mono<MemberDTO.MemberLoginInfo> memberLoginInfoResponse() {

        MemberDTO.MemberLoginInfo memberLoginInfo = memberLoginInfo();

        return Mono.just(memberLoginInfo);
    }

    /**
     * 회원 로그인 Response 구성
     */
    public static MemberLoginResponse memberLoginResponse() {

        MemberDTO.MemberLoginInfo memberLoginInfo = memberLoginInfo();

        return MemberLoginResponse.builder()
            .memberToken(memberLoginInfo.getMemberToken())
            .memberName(memberName)
            .accessToken(memberLoginInfo.getAccessToken())
            .refreshToken(memberLoginInfo.getRefreshToken())
            .build();
    }

    /**
     * 회원 로그인 Request 구성
     */
    public static MemberLoginRequest memberLoginRequest() {

        return MemberLoginRequest.builder()
            .memberLoginId("로그인아이디")
            .memberPassword("123!@#")
            .build();
    }
}
