package msa.study.house.infrastructure.exception.status;

import org.springframework.http.HttpStatus;
import msa.study.house.infrastructure.exception.GlobalException;

public class RegisterFailException extends GlobalException {

    public RegisterFailException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public RegisterFailException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public RegisterFailException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
