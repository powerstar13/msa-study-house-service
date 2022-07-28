package station3.assignment.member.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

    @Builder.Default
    private Map<String, Object> response = new LinkedHashMap<>();
    
    public void add(String key, Object value) {
        this.response.put(key, value);
    }

    @Override
    public String toString() {
        return "{"
            + "\"response\":" + response
            + "}";
    }
}
