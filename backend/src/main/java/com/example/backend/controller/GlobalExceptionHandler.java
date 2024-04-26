package com.example.backend.controller;

import com.example.backend.auth.exception.DuplicateOAuth2ProviderException;
import com.example.backend.board.exception.AuthorForbiddenException;
import com.example.backend.chat.exception.MemberAlreadyInvitedException;
import com.example.backend.oauth2.exception.OAuth2InvalidEmailException;
import com.example.backend.security.exception.InvalidJwtException;
import com.example.backend.security.exception.InvalidRefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.format.DateTimeParseException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //400
    @ExceptionHandler({
            InvalidDataAccessApiUsageException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            MissingRequestCookieException.class,
            InvalidRefreshToken.class,
            MethodArgumentNotValidException.class,
            DateTimeParseException.class
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

    //403
    @ExceptionHandler({
            AuthorForbiddenException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleForbiddenException(Exception ex) {
        log.error("Forbidden", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    //409
    @ExceptionHandler({
            DuplicateOAuth2ProviderException.class,
            MemberAlreadyInvitedException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleCustomException(Exception ex) {
        log.error("Conflict", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    //422
    @ExceptionHandler({
            OAuth2InvalidEmailException.class
    })
    public ResponseEntity<Object> handleUnprocessableEntityException(Exception ex) {
        log.error("Unprocessable Entity", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        log.error("Internal Server Error", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
