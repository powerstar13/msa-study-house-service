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

    private int page = 1; // 요청 페이지
    private int size = 10; // 한 페이지 당 보여질 개수

    /**
     * Pageable에 사용될 page는 0부터 시작해야 한다.(0 = 첫 페이지)
     */
    public int getPageForPageable() {
        return this.page - 1;
    }

    @Override
    public String toString() {
        return "{"
            + "\"page\":" + page
            + ", \"size\":" + size
            + "}";
    }
}
