package msa.study.house.presentation.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.study.house.infrastructure.exception.status.BadRequestException;
import msa.study.house.infrastructure.exception.status.ExceptionMessage;
import msa.study.house.presentation.shared.request.RequestVerify;
import msa.study.house.domain.RentalType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRegisterRequest implements RequestVerify {

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
            + "\"rentalType\":\"" + rentalType + "\""
            + ", \"deposit\":" + deposit
            + ", \"rent\":" + rent
            + "}";
    }
}
