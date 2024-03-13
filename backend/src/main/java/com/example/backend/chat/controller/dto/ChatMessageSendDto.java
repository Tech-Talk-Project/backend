package com.example.backend.chat.controller.dto;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageSendDto {
    private Long senderId;
    private String content;
    private Date sendTime;

    public ChatMessageSendDto(ChatRoom.LastMessage message) {
        this.senderId = message.getSenderId();
        this.content = message.getContent();
        this.sendTime = message.getSendTime();
    }
}
