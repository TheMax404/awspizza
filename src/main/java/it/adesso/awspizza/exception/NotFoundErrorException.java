package it.adesso.awspizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundErrorException extends RuntimeException {

    public NotFoundErrorException() {
        super();
    }

    public NotFoundErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundErrorException(String message) {
        super(message);
    }

    public NotFoundErrorException(Throwable cause) {
        super(cause);
    }
}
