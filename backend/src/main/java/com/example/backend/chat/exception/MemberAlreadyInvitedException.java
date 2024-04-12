package com.example.backend.chat.exception;

public class MemberAlreadyInvitedException extends RuntimeException {
    public MemberAlreadyInvitedException(String message) {
        super(message);
    }
}
