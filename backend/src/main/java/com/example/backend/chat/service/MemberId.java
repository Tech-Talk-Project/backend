package com.example.backend.chat.service;

public enum MemberId {
    ADMIN(-1L);
    private final Long value;

    MemberId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
