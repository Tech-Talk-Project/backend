package com.example.backend.chat.dto;

import com.example.backend.chat.domain.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ChatMessageDto {
    private Long senderId;
    private Date sendTime;
    private String content;

    public ChatMessageDto(ChatMessage chatMessage) {
        this.senderId = chatMessage.getSenderId();
        this.sendTime = chatMessage.getSendTime();
        this.content = chatMessage.getContent();
    }
}
