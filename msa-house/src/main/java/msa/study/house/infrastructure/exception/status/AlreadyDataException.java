package msa.study.house.infrastructure.exception.status;

import org.springframework.http.HttpStatus;
import msa.study.house.infrastructure.exception.GlobalException;

public class AlreadyDataException extends GlobalException {

    public AlreadyDataException() {
        super(HttpStatus.CONFLICT);
    }

    public AlreadyDataException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

    public AlreadyDataException(String reason, Throwable cause) {
        super(HttpStatus.CONFLICT, reason, cause);
    }
}
