package msa.study.house.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.study.house.presentation.shared.response.PageResponseDTO;
import msa.study.house.presentation.response.dto.HouseInfoResponseDTO;
import msa.study.house.presentation.shared.response.SuccessResponse;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HousePageResponse extends SuccessResponse {

    private PageResponseDTO pageInfo; // 페이지 정보
    private List<HouseInfoResponseDTO> houseList; // 데이터 목록

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"pageInfo\":" + pageInfo
            + ", \"houseList\":" + houseList
            + "}";
    }
}
