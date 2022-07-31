package station3.assignment.house.presentation.shared.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    private Integer page = 1; // 요청 페이지
    private Integer size = 10; // 한 페이지 당 보여질 개수

    @Override
    public String toString() {
        return "{"
            + "\"page\":" + page
            + ", \"size\":" + size
            + "}";
    }
}
