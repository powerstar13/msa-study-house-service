package station3.assignment.member.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest("BadRequestException", "Request를 전달해주세요."),
    IsRequiredAuthorization("UnauthorizedException", "토큰을 전달해 주세요."),

    InvalidMemberLogin("UnauthorizedException", "아이디 또는 비밀번호가 틀렸습니다."),
    InvalidMemberVerify("UnauthorizedException", "회원 인증에 실패했습니다."),

    AlreadyDataMember("AlreadyDataException", "이미 존재하는 회원입니다."),

    RegisterFailMember("RegisterFailException", "회원 가입에 실패했습니다. 관리자에게 문의 바랍니다."),

    NotFoundMember("NotFoundDataException", "조회된 회원 정보가 없습니다.");

    private final String type;
    private final String message;
}
