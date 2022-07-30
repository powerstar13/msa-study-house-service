package station3.assignment.house.domain.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonDateEntity {
    
    @CreatedDate
    @Column(value = "createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt; // 생성일
    
    @LastModifiedDate
    @Column(value = "updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt; // 수정일

    @Override
    public String toString() {
        return "{"
            + "\"createdAt\":" + createdAt
            + ", \"updatedAt\":" + updatedAt
            + "}";
    }
}
