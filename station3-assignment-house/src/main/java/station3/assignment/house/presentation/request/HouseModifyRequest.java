package station3.assignment.house.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.infrastructure.exception.status.BadRequestException;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.presentation.request.dto.RentalModifyRequest;
import station3.assignment.house.presentation.request.dto.RentalRegisterRequest;
import station3.assignment.house.presentation.shared.request.RequestVerify;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseModifyRequest implements RequestVerify {

    private String houseToken; // 방 대체 식별키
    private String houseAddress; // 방 주소
    private HouseType houseType; // 방 유형
    private List<RentalModifyRequest> rentalModifyRequestList; // 임대료 등록 정보 목록

    @Override
    public void verify() {
        if (StringUtils.isBlank(houseToken)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseToken.getMessage());
        if (StringUtils.isBlank(houseAddress)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseAddress.getMessage());
        if (houseType == null) throw new BadRequestException(ExceptionMessage.IsRequiredHouseType.getMessage());

        if (rentalModifyRequestList != null) rentalModifyRequestList.forEach(RentalModifyRequest::verify); // 임대료 정보 Request 유효성 검사
    }
}
