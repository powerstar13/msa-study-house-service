package station3.assignment.member.presentation.shared.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class SuccessResponse {
    
    private final int rt = HttpStatus.OK.value();
    
    private final String rtMsg = HttpStatus.OK.getReasonPhrase();

    @Override
    public String toString() {
        return "{"
            + "\"rt\":" + rt
            + ", \"rtMsg\":\"" + rtMsg + "\""
            + "}";
    }
}
