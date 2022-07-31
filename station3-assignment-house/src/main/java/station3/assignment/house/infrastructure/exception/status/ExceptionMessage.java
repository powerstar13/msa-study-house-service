package station3.assignment.house.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest("BadRequestException", "Request를 전달해주세요."),
    IsRequiredMemberToken("BadRequestException", "회원 대체 식별키를 전달해주세요."),
    IsRequiredHouseToken("BadRequestException", "방 대체 식별키를 전달해주세요."),
    IsRequiredHouseAddress("BadRequestException", "방 주소를 입력해주세요."),
    IsRequiredHouseType("BadRequestException", "방 유형을 입력해주세요."),
    IsRequiredRentalType("BadRequestException", "임대 유형을 입력해주세요."),
    IsRequiredDeposit("BadRequestException", "보증금을 입력해주세요."),
    IsRequiredRent("BadRequestException", "월세를 입력해주세요."),

    RegisterFailHouse("RegisterFailException", "내방 등록에 실패했습니다. 관리자에게 문의 바랍니다."),
    RegisterFailRental("RegisterFailException", "내방 임대료 등록에 실패했습니다. 관리자에게 문의 바랍니다."),

    NotFoundHouse("NotFoundDataException", "조회된 방 정보가 없습니다."),
    serverError("RuntimeException", "서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다.");

    private final String type;
    private final String message;
}
