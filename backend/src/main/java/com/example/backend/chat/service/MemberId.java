package com.example.backend.chat.service;

public enum MemberId {
    INVITE(-3L),
    LEAVE(-2L),
    ADMIN(-1L);
    private final Long value;

    MemberId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
