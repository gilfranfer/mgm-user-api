package org.gil.mgm.users.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.gil.mgm.users.api.controller.UsersApiController;
import org.gil.mgm.users.api.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.gil.mgm.users.api.exception.ErrorCodes.*;


@RestControllerAdvice
public class ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);
    public static final String REQUIRED_REQUEST_BODY_MISSING_EXCEPTION_MESSAGE = "Required request body is missing";

    /**
     * Handle not found exception.
     *
     * @param ex the exception thrown
     * @return error response
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
        ResourceNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        ErrorResponse response = buildErrorResponse(RESOURCE_NOT_FOUND, ex.getMessage());
        log.error("Internal exception occurred {}", response, ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle generic exception.
     *
     * @param ex the exception thrown
     * @return Error response
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response =
            buildErrorResponse(INTERNAL_EXCEPTION_ERROR_CODE, ex.getMessage());
        log.error("Internal exception occurred {}", response, ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     *  This method handles exceptions triggered before the request reaches the Controller.
     *  - MethodArgumentTypeMismatchException: When not able to convert a Parameter to its expected object type.
     *  - ConstraintViolationException: When a required property is not provided or null.
     *  - HttpMessageNotReadableException: When not able to convert a property value to its Enum value.
     *  - MissingServletRequestParameterException: When a required Query Param was not provided.
     *  - MethodArgumentNotValidException: When an argument annotated with validation annotations
     *    (like @NotNull, @Size, @Min, etc.) fails validation.
     * @param ex the exception thrown
     * @param request the web request
     * @return ResponseEntity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
        MethodArgumentTypeMismatchException.class, IllegalArgumentException.class,
        HttpMessageNotReadableException.class, MissingServletRequestParameterException.class,
        MissingRequestHeaderException.class, MethodArgumentNotValidException.class,
        ValidationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleMultipleBadRequestExceptions(Exception ex, WebRequest request) {
        ErrorResponse response;

        if (ex instanceof MissingRequestHeaderException) {
            response = buildErrorResponse(
                REQUIRED_HEADER_MISSING,
                ((MissingRequestHeaderException) ex).getHeaderName());
        } else if (ex instanceof MethodArgumentNotValidException) {
            String validationErrors = ((MethodArgumentNotValidException) ex).getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(". "));
            response = buildErrorResponse(INPUT_VALIDATION_FAILED, validationErrors);
        } else if (ex instanceof HttpMessageNotReadableException && nonNull(ex.getMessage())
                    && ex.getMessage().contains(REQUIRED_REQUEST_BODY_MISSING_EXCEPTION_MESSAGE)) {
            response = buildErrorResponse(REQUIRED_REQUEST_BODY_MISSING);
        } else if (ex instanceof HttpMessageNotReadableException
            && ex.getCause() instanceof InvalidFormatException exception) {
            String invalidValue = exception.getValue().toString();
            String propertyPath = exception.getPathReference();

            if (exception.getPath() != null && !exception.getPath().isEmpty()) {
                // Build the full property path dynamically
                StringBuilder propertyChain = new StringBuilder();
                exception.getPath().forEach(reference -> {
                    if (!propertyChain.isEmpty()) {
                        propertyChain.append(".");
                    }
                    propertyChain.append(reference.getFieldName());
                });
                propertyPath = propertyChain.toString();
            }
            response = buildErrorResponse(INVALID_DATA_VALUE, propertyPath, invalidValue);
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            response = buildErrorResponse(INVALID_DATA_VALUE,
                ((MethodArgumentTypeMismatchException) ex).getName(),
                String.valueOf(((MethodArgumentTypeMismatchException) ex).getValue())
            );
        } else {
            response = buildErrorResponse(GENERIC_BAD_REQUEST, ex.getMessage());
        }

        log.error("Bad Request exception occurred {}", response, ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(
        final String errorCode, final String... messageArguments
    ) {
        return new ErrorResponse(
            errorCode,
            getFormattedErrorMessage(errorCode, messageArguments));
    }

}
