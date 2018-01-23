package com.auth0.samples.authapi.exception;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.NoPermissionException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoPermissionException.class})
	@ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Map<String, String> noPermission(NoPermissionException e) {
        return createErrorResponse(e, "Not Allowed");
    }

	@ExceptionHandler({UserExistsException.class})
	@ResponseBody
	@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
	public Map<String, String> userExists(UserExistsException e) {
		return createErrorResponse(e, "This username already exists");
	}

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> exceptionInProcessing(Exception e) {
        return createErrorResponse(e, "Unable to process. Unknown error occurred: " + e.getMessage());
    }

    private Map<String, String> createErrorResponse(Exception e, String errorMessage) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", errorMessage);
        errorResponse.put("reason", e.toString());
        return errorResponse;
    }
}
