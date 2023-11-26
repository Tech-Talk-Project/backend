package com.example.backend.controller;

import com.example.backend.auth.exception.DuplicateOAuth2ProviderException;
import com.example.backend.security.exception.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    //400
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
    })
    @ResponseBody
    public ResponseEntity<Object> handleBadRequestException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //401
    @ExceptionHandler({
            BadCredentialsException.class,
            InvalidJwtException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleUnAuthorizedException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    //409
    @ExceptionHandler({
            DuplicateOAuth2ProviderException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleCustomException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
