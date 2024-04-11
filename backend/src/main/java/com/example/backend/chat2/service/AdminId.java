package com.example.backend.chat2.service;

public enum AdminId {
    WELCOME(-1L),
    LEAVE(-2L),
    INVITE(-3L),
    NEW_OWNER(-1L),
    TEMP(-1L);

    private final Long value;

    AdminId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
