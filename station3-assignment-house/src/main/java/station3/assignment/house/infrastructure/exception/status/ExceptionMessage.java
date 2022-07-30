package station3.assignment.house.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest("BadRequestException", "Request를 전달해주세요."),

    RegisterFailHouse("RegisterFailException", "내방 등록에 실패했습니다. 관리자에게 문의 바랍니다."),
    RegisterFailRental("RegisterFailException", "내방 거래유형 등록에 실패했습니다. 관리자에게 문의 바랍니다."),

    NotFoundHouse("NotFoundDataException", "조회된 내방 정보가 없습니다.");

    private final String type;
    private final String message;
}
