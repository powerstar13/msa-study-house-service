package station3.assignment.member.infrastructure.exception.status;

import org.springframework.http.HttpStatus;
import station3.assignment.member.infrastructure.exception.GlobalException;

public class BadRequestException extends GlobalException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public BadRequestException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
