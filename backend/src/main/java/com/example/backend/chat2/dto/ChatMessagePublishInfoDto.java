package com.example.backend.chat2.dto;

import com.example.backend.chat2.domain.ChatRoom;
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
}
