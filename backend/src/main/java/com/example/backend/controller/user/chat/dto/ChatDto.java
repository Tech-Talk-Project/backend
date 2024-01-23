package com.example.backend.controller.user.chat.dto;

import lombok.Data;

@Data
public class ChatDto {
    private Long memberId;
    private String message;
    private String nickname;
}
