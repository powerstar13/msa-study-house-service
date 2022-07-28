package station3.assignment.member.presentation.shared.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CreatedSuccessResponse {
    
    private final int rt = HttpStatus.CREATED.value();
    
    private final String rtMsg = HttpStatus.CREATED.getReasonPhrase();
    
    @Override
    public String toString() {
        return "{"
            + "\"rt\":" + rt
            + ", \"rtMsg\":\"" + rtMsg + "\""
            + "}";
    }
}
