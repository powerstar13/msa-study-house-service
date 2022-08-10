package msa.study.house.presentation.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.study.house.domain.RentalType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalInfoResponseDTO {

    private String rentalToken; // 임대료 대체 식별키
    private RentalType rentalType; // 임대 유형
    private int deposit; // 보증금
    private Integer rent; // 월세

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
