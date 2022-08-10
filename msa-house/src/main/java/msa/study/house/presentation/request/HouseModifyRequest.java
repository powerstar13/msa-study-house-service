package msa.study.house.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.study.house.domain.HouseType;
import msa.study.house.infrastructure.exception.status.BadRequestException;
import msa.study.house.infrastructure.exception.status.ExceptionMessage;
import msa.study.house.presentation.shared.request.RequestVerify;
import org.apache.commons.lang3.StringUtils;
import msa.study.house.presentation.request.dto.RentalModifyRequest;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseModifyRequest implements RequestVerify {

    private String houseToken; // 방 대체 식별키
    private String houseAddress; // 방 주소
    private HouseType houseType; // 방 유형
    private List<RentalModifyRequest> rentalList; // 임대료 목록

    @Override
    public void verify() {
        if (StringUtils.isBlank(houseToken)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseToken.getMessage());
        if (StringUtils.isBlank(houseAddress)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseAddress.getMessage());
        if (houseType == null) throw new BadRequestException(ExceptionMessage.IsRequiredHouseType.getMessage());

        if (rentalList == null) throw new BadRequestException(ExceptionMessage.IsRequiredRentalInfo.getMessage());
        rentalList.forEach(RentalModifyRequest::verify); // 임대료 정보 Request 유효성 검사
    }

    @Override
    public String toString() {
        return "{"
            + "\"houseToken\":\"" + houseToken + "\""
            + ", \"houseAddress\":\"" + houseAddress + "\""
            + ", \"houseType\":\"" + houseType + "\""
            + ", \"rentalList\":" + rentalList
            + "}";
    }
}
