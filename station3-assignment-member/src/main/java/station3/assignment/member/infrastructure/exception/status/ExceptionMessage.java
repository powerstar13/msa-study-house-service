package station3.assignment.member.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest("BadRequestException", "Request를 전달해주세요."),
    IsRequiredMemberType("BadRequestException", "회원 유형을 전달해 주세요."),
    IsRequiredMemberLoginId("BadRequestException", "회원 로그인 아이디를 입력해 주세요."),
    IsRequiredMemberPassword("BadRequestException", "회원 비밀번호를 입력해 주세요."),
    IsRequiredMemberName("BadRequestException", "회원 이름을 입력해 주세요."),
    IsRequiredMemberBirth("BadRequestException", "회원 생년월일을 입력해 주세요."),
    IsRequiredMemberPhone("BadRequestException", "회원 연락처을 입력해 주세요."),
    IsRequiredMemberToken("BadRequestException", "회원 대체 식별키를 전달해 주세요."),

    InvalidMemberLogin("UnauthorizedException", "아이디 또는 비밀번호가 틀렸습니다."),
    InvalidMemberVerify("UnauthorizedException", "회원 인증에 실패했습니다."),

    AlreadyDataMember("AlreadyDataException", "이미 존재하는 회원입니다."),

    RegisterFailMember("RegisterFailException", "회원 가입에 실패했습니다. 관리자에게 문의 바랍니다."),

    NotFoundMember("NotFoundDataException", "조회된 회원 정보가 없습니다.");

    private final String type;
    private final String message;
}
