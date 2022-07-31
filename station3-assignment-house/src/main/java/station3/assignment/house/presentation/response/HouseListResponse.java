package station3.assignment.house.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import station3.assignment.house.presentation.response.dto.HouseInfoResponseDTO;
import station3.assignment.house.presentation.shared.response.SuccessResponse;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseListResponse extends SuccessResponse {

    private List<HouseInfoResponseDTO> houseList; // 방 목록

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"houseList\":" + houseList
            + "}";
    }
}
