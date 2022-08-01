package station3.assignment.house.presentation.shared.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO {

    private int currentSize; // 현재 페이지의 데이터 수
    private int currentPage; // 현재 페이지 번호
    private long totalCount; // 전체 데이터 수
    private int totalPage; // 총 페이지 수

    @Override
    public String toString() {
        return "{"
            + "\"currentSize\":" + currentSize
            + ", \"currentPage\":" + currentPage
            + ", \"totalCount\":" + totalCount
            + ", \"totalPage\":" + totalPage
            + "}";
    }
}
