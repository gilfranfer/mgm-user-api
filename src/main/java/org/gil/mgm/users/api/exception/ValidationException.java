package org.gil.mgm.users.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ValidationException extends RuntimeException {

    /**
     * @param message - Detail message
     */
    public ValidationException(String message) {
        super(message);
    }

}
