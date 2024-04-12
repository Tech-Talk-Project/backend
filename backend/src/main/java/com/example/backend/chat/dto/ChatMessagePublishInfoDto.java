package com.example.backend.chat.dto;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.websocket.ChatMessageReceiveDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessagePublishInfoDto {
    private Long senderId;
    private String content;
    private LocalDateTime sendTime;

    public ChatMessagePublishInfoDto(ChatRoom.Message message) {
        this.senderId = message.getSenderId();
        this.content = message.getContent();
        this.sendTime = message.getSendTime();
    }

    public ChatMessagePublishInfoDto(Long senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        this.sendTime = LocalDateTime.now();
    }

    public ChatMessagePublishInfoDto(ChatMessageReceiveDto dto) {
        this.senderId = dto.getMemberId();
        this.content = dto.getContent();
        this.sendTime = LocalDateTime.now();
    }
}
