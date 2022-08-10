package msa.study.house.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.study.house.presentation.shared.response.CreatedSuccessResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseRegisterResponse extends CreatedSuccessResponse {

    private String houseToken; // 내방 대체 식별키

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"houseToken\":\"" + houseToken + "\""
            + "}";
    }
}
