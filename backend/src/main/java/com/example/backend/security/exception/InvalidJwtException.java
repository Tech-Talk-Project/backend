package com.example.backend.security.exception;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String token, String message) {
        super(String.format("[JWT] %s / message : %s", message, token));
    }
}
