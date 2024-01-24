package com.example.backend.chat.controller.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    private Long memberId;
    private String content;
}
