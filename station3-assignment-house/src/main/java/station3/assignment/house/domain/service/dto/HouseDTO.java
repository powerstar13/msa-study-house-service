package station3.assignment.house.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;

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

        private House house;
        private Flux<Rental> rentalFlux;

        @Override
        public String toString() {
            return "{"
                + "\"house\":" + house
                + ", \"rentalFlux\":" + rentalFlux
                + "}";
        }
    }
}
