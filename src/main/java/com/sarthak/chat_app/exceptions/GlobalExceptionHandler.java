package com.sarthak.chat_app.exceptions;

import com.sarthak.chat_app.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidAuthenticationToken.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleInvalidToken(InvalidAuthenticationToken ex) {
        logger.warn("Invalid Token: {}", ex.getMessage());
        return new ApiResponse(false, null, "Invalid Token", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleInternalServerError(Exception ex) {
        logger.error("Internal Server Error: {}", ex.getMessage());
        return new ApiResponse(false, null, "Something went wrong", ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleResourceNotFound(ResourceNotFoundException ex) {
        logger.warn("Resource Not Found: {}", ex.getMessage());
        return new ApiResponse(false, null, "Resource Not Found", ex.getMessage());
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleBadRequest(BadRequest ex) {
        logger.warn("Bad Request: {}", ex.getMessage());
        return new ApiResponse(false, null, "Bad Request", ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleUsernameNotFound(UsernameNotFoundException ex) {
        logger.warn("User Not Found: {}", ex.getMessage());
        return new ApiResponse(false, null, "User Not Found", ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleAuthentication(AuthenticationException ex) {
        logger.error("Authentication Failed: {}", ex.getMessage());
        return new ApiResponse(false, null, "Authentication Failed", ex.getMessage());
    }
}
