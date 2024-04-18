package com.example.backend.board.exception;

public class AuthorForbiddenException extends RuntimeException {
    public AuthorForbiddenException(String message) {
        super(message);
    }
}
