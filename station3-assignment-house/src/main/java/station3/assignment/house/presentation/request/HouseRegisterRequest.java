package station3.assignment.house.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.infrastructure.exception.status.BadRequestException;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.presentation.request.dto.RentalRegisterRequest;
import station3.assignment.house.presentation.shared.request.RequestVerify;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseRegisterRequest implements RequestVerify {

    private String memberToken; // 회원 대체 식별키
    private String houseAddress; // 방 주소
    private HouseType houseType; // 방 유형
    private List<RentalRegisterRequest> rentalList; // 임대료 정보 목록

    @Override
    public void verify() {
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());
        if (StringUtils.isBlank(houseAddress)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseAddress.getMessage());
        if (houseType == null) throw new BadRequestException(ExceptionMessage.IsRequiredHouseType.getMessage());

        if (rentalList == null) throw new BadRequestException(ExceptionMessage.IsRequiredRentalInfo.getMessage());
        rentalList.forEach(RentalRegisterRequest::verify); // 임대료 정보 Request 유효성 검사
    }

    @Override
    public String toString() {
        return "{"
            + "\"memberToken\":\"" + memberToken + "\""
            + ", \"houseAddress\":\"" + houseAddress + "\""
            + ", \"houseType\":\"" + houseType + "\""
            + ", \"rentalList\":" + rentalList
            + "}";
    }
}
