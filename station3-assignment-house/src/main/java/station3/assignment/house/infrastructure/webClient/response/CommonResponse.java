package station3.assignment.house.infrastructure.webClient.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    private int rt;
    private String rtMsg;

    @Override
    public String toString() {
        return "{"
            + "\"rt\":" + rt
            + ", \"rtMsg\":\"" + rtMsg + "\""
            + "}";
    }
}
