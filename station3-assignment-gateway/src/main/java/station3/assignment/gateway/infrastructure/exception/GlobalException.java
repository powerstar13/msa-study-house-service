package station3.assignment.gateway.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// ResponseStatusException을 상속받아 공통 처리를 받을 수 있는 에러 처리
public class GlobalException extends ResponseStatusException {

    public GlobalException(HttpStatus status) {
        super(status);
    }

    public GlobalException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public GlobalException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
