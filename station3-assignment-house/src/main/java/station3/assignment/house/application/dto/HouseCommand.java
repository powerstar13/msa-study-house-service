package station3.assignment.house.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.RentalType;
import station3.assignment.house.presentation.shared.request.PageRequestDTO;

import java.util.List;
import java.util.UUID;

public class HouseCommand {

    @Getter
    @Builder
    public static class HouseRegister {

        private String memberToken; // 회원 대체 식별키
        private String houseAddress; // 방 주소
        private HouseType houseType; // 방 유형
        private List<RentalRegisterDTO> rentalList; // 임대료 목록

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

    @Getter
    @Builder
    public static class ExchangedHouseRegister {

        private int memberId; // 회원 고유번호
        private String houseAddress; // 방 주소
        private HouseType houseType; // 방 유형
        private List<RentalRegisterDTO> rentalList; // 임대료 정보 목록

        public House toEntity() {

            return House.builder()
                .houseToken(UUID.randomUUID().toString())
                .memberId(this.memberId)
                .houseAddress(this.houseAddress)
                .houseType(this.houseType)
                .build();
        }

        @Override
        public String toString() {
            return "{"
                + "\"memberId\":" + memberId
                + ", \"houseAddress\":\"" + houseAddress + "\""
                + ", \"houseType\":\"" + houseType + "\""
                + ", \"rentalList\":" + rentalList
                + "}";
        }
    }

    @Getter
    @Builder
    public static class RentalRegisterDTO {

        private RentalType rentalType; // 임대 유형
        private Integer deposit; // 보증금
        private Integer rent; // 월세

        public Rental toEntity(int houseId) {

            return Rental.builder()
                .houseId(houseId)
                .rentalToken(UUID.randomUUID().toString())
                .rentalType(this.rentalType)
                .deposit(this.deposit)
                .rent(this.rent)
                .build();
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

    @Getter
    @Builder
    public static class HouseModify {

        private String houseToken; // 방 대체 식별키
        private String houseAddress; // 방 주소
        private HouseType houseType; // 방 유형
        private List<RentalModifyDTO> rentalList; // 임대료 목록

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

    @Getter
    @Builder
    public static class RentalModifyDTO {

        private String rentalToken; // 임대료 대체 식별키
        private RentalType rentalType; // 임대 유형
        private Integer deposit; // 보증금
        private Integer rent; // 월세

        public Rental toEntity(int houseId) {

            return Rental.builder()
                .houseId(houseId)
                .rentalToken(UUID.randomUUID().toString())
                .rentalType(this.rentalType)
                .deposit(this.deposit)
                .rent(this.rent)
                .build();
        }

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

    @Getter
    @SuperBuilder
    public static class HouseDTOPage extends PageRequestDTO {

        private HouseType houseType; // 방 유형
        private RentalType rentalType; // 임대 유형
        private Integer depositStartRange; // 보증금 시작 범위
        private Integer depositEndRange; // 보증금 끝 범위
        private Integer rentStartRange; // 월세 시작 범위
        private Integer rentEndRange; // 월세 끝 범위

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
}
