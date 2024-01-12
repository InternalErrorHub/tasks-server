package net.internalerror.rest.exception;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Generated
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }


}
