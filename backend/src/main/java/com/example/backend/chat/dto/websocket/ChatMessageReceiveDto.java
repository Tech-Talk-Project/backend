package com.example.backend.chat.dto.websocket;

import lombok.Data;

@Data
public class ChatMessageReceiveDto {
    private Long memberId;
    private String content;
}
