package com.example.backend.chat.dto.websocket;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageSendDto {
    private Long senderId;
    private String content;
    private LocalDateTime sendTime;
}
