package org.gil.mgm.users.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {

    /**
     * @param message - Detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
