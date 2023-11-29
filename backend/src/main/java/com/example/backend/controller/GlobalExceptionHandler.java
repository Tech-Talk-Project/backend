package com.example.backend.controller;

import com.example.backend.auth.exception.DuplicateOAuth2ProviderException;
import com.example.backend.security.exception.InvalidJwtException;
import com.example.backend.security.exception.InvalidRefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //400
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            MissingRequestCookieException.class,
            InvalidRefreshToken.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleBadRequestException(Exception ex) {
        log.error("Bad Request", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //401
    @ExceptionHandler({
            BadCredentialsException.class,
            InvalidJwtException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleUnAuthorizedException(Exception ex) {
        log.error("Unauthorized", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    //409
    @ExceptionHandler({
            DuplicateOAuth2ProviderException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleCustomException(Exception ex) {
        log.error("Conflict", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        log.error("Internal Server Error", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
