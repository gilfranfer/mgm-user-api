package org.gil.mgm.users.api.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ErrorCodes {

    protected static final Map<String, String> ERROR_MESSAGES = new HashMap<>();

    public static final String NOT_ERROR_MESSAGE_DEFINED =
        "No error message has been defined for this error code. Please contact Support team.";

    public static final String INTERNAL_EXCEPTION_ERROR_CODE = "ERR-00";
    public static final String RESOURCE_NOT_FOUND = "ERR-01";
    public static final String GENERIC_BAD_REQUEST = "ERR-02";
    public static final String REQUIRED_REQUEST_BODY_MISSING = "ERR-03";
    public static final String REQUIRED_HEADER_MISSING = "ERR-04";
    public static final String INVALID_DATA_VALUE = "ERR-05";
    public static final String INPUT_VALIDATION_FAILED = "ERR-06";

    static {
        ERROR_MESSAGES.put(INTERNAL_EXCEPTION_ERROR_CODE,
            "Unable to process the request at the moment; please try again later. Error: {0}");
        ERROR_MESSAGES.put(RESOURCE_NOT_FOUND, "Resource was not found. {0}");
        ERROR_MESSAGES.put(GENERIC_BAD_REQUEST, "Bad request: {0}");
        ERROR_MESSAGES.put(REQUIRED_REQUEST_BODY_MISSING, "Required request body is missing.");
        ERROR_MESSAGES.put(REQUIRED_HEADER_MISSING, "The required header {0} is missing.");
        ERROR_MESSAGES.put(INVALID_DATA_VALUE, "Invalid value provided for {0}; Value: {1}");
        ERROR_MESSAGES.put(INPUT_VALIDATION_FAILED, "Validation failed: {0}.");
    }

    /**
     * Generates an error message for the specified error code,
     * interpolating any provided arguments.
     *
     * @param errorCode the unique identifier for the error message template.
     * @param args the arguments to be interpolated within the error message template.
     * @return a formatted error message with the specified arguments,
     * or a default message if the error code is not found.
     */
    public static String getFormattedErrorMessage(String errorCode, String... args) {
        return MessageFormat.format(ERROR_MESSAGES.getOrDefault(errorCode, NOT_ERROR_MESSAGE_DEFINED), args);
    }

}
