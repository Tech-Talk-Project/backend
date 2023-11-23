package com.example.backend.oauth2.exception;

public class OAuth2InvalidEmailException extends RuntimeException {
    public OAuth2InvalidEmailException(String message) {
        super(message);
    }
}
