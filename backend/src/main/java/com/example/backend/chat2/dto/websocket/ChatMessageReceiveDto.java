package com.example.backend.chat2.dto.websocket;

import lombok.Data;

@Data
public class ChatMessageReceiveDto {
    private Long memberId;
    private String content;
}
