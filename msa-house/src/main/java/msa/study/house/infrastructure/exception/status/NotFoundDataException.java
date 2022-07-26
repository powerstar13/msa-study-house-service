package msa.study.house.infrastructure.exception.status;

import org.springframework.http.HttpStatus;
import msa.study.house.infrastructure.exception.GlobalException;

public class NotFoundDataException extends GlobalException {

    public NotFoundDataException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundDataException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public NotFoundDataException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
