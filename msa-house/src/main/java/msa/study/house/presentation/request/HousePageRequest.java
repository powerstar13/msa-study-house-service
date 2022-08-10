package msa.study.house.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import msa.study.house.presentation.shared.request.PageRequestDTO;
import msa.study.house.domain.HouseType;
import msa.study.house.domain.RentalType;
import msa.study.house.presentation.shared.request.RequestVerify;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HousePageRequest extends PageRequestDTO implements RequestVerify {

    private HouseType houseType; // 방 유형
    private RentalType rentalType; // 임대 유형
    private Integer depositStartRange; // 보증금 시작 범위
    private Integer depositEndRange; // 보증금 끝 범위
    private Integer rentStartRange; // 월세 시작 범위
    private Integer rentEndRange; // 월세 끝 범위

    @Override
    public void verify() {}

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"houseType\":\"" + houseType + "\""
            + ", \"rentalType\":\"" + rentalType + "\""
            + ", \"depositStartRange\":" + depositStartRange
            + ", \"depositEndRange\":" + depositEndRange
            + ", \"rentStartRange\":" + rentStartRange
            + ", \"rentEndRange\":" + rentEndRange
            + "}";
    }
}
