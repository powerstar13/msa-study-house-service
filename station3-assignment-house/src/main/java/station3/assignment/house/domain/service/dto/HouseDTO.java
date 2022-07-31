package station3.assignment.house.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.RentalType;

import java.util.List;

public class HouseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HouseTokenInfo {

        private String houseToken; // 방 대체 식별키

        @Override
        public String toString() {
            return "{"
                + "\"houseToken\":\"" + houseToken + "\""
                + "}";
        }
    }

    @Getter
    @Builder
    public static class HouseAggregate {

        private House house; // 방 레퍼런스
        private Flux<Rental> rentalFlux; // 임대료 목록 레퍼런스

        @Override
        public String toString() {
            return "{"
                + "\"house\":" + house
                + ", \"rentalFlux\":" + rentalFlux
                + "}";
        }
    }

    @Getter
    @Builder
    public static class HouseInfo {

        private String houseToken; // 방 대체 식별키
        private String houseAddress; // 방 주소
        private HouseType houseType; // 방 유형
        private List<RentalInfo> rentalList; // 임대료 목록

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
    public static class RentalInfo {

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

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HouseList {

        private List<HouseInfo> houseList; // 방 목록

        @Override
        public String toString() {
            return "{"
                + "\"houseList\":" + houseList
                + "}";
        }
    }
}
