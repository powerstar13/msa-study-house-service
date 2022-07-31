package station3.assignment.house.presentation.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.presentation.shared.response.SuccessResponse;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseInfoResponseDTO {

    private String houseToken; // 방 대체 식별키
    private String houseAddress; // 방 주소
    private HouseType houseType; // 방 유형
    private List<RentalInfoResponseDTO> rentalList; // 임대료 목록

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"houseToken\":\"" + houseToken + "\""
            + ", \"houseAddress\":\"" + houseAddress + "\""
            + ", \"houseType\":\"" + houseType + "\""
            + ", \"rentalList\":" + rentalList
            + "}";
    }
}
