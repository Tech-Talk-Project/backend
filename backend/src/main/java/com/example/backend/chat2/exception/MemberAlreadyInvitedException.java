package com.example.backend.chat2.exception;

public class MemberAlreadyInvitedException extends RuntimeException {
    public MemberAlreadyInvitedException(String message) {
        super(message);
    }
}
