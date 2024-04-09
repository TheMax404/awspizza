package it.adesso.awspizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestErrorException extends RuntimeException {

    public BadRequestErrorException() {
        super();
    }

    public BadRequestErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestErrorException(String message) {
        super(message);
    }

    public BadRequestErrorException(Throwable cause) {
        super(cause);
    }
}
