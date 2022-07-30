package station3.assignment.house.application.dto;

import lombok.Builder;
import lombok.Getter;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.domain.RentalType;

import java.util.List;

public class HouseCommand {

    @Getter
    @Builder
    public static class HouseRegister {

        private String memberToken; // 회원 대체 식별키
        private String houseAddress; // 방 주소
        private HouseType houseType; // 방 유형
        private List<RentalRegisterDTO> rentalRegisterDTOList; // 임대료 정보 목록

        @Override
        public String toString() {
            return "{"
                + "\"memberToken\":\"" + memberToken + "\""
                + ", \"houseAddress\":\"" + houseAddress + "\""
                + ", \"houseType\":\"" + houseType + "\""
                + ", \"rentalRegisterDTOList\":" + rentalRegisterDTOList
                + "}";
        }
    }

    @Getter
    @Builder
    public static class RentalRegisterDTO {

        private RentalType rentalType; // 임대 유형
        private Integer deposit; // 보증금
        private Integer rent; // 월세

        @Override
        public String toString() {
            return "{"
                + "\"rentalType\":\"" + rentalType + "\""
                + ", \"deposit\":" + deposit
                + ", \"rent\":" + rent
                + "}";
        }
    }
}
