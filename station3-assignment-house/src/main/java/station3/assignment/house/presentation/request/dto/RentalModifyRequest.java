package station3.assignment.house.presentation.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import station3.assignment.house.domain.RentalType;
import station3.assignment.house.infrastructure.exception.status.BadRequestException;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.presentation.shared.request.RequestVerify;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalModifyRequest implements RequestVerify {

    private String rentalToken; // 임대료 대체 식별키 (수정할 경우 필수)
    private RentalType rentalType; // 임대 유형
    private Integer deposit; // 보증금
    private Integer rent; // 월세

    @Override
    public void verify() {
        if (rentalType == null) throw new BadRequestException(ExceptionMessage.IsRequiredRentalType.getMessage());
        if (deposit == null) throw new BadRequestException(ExceptionMessage.IsRequiredDeposit.getMessage());
        if (rentalType.equals(RentalType.MONTHLY) && rent == null) throw new BadRequestException(ExceptionMessage.IsRequiredRent.getMessage());
    }

    @Override
    public String toString() {
        return "{"
            + "\"rentalToken\":\"" + rentalToken + "\""
            + ", \"rentalType\":\"" + rentalType + "\""
            + ", \"deposit\":" + deposit
            + ", \"rent\":" + rent
            + "}";
    }
}
