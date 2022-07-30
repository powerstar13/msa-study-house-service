package station3.assignment.house.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
}
