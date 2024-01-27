package com.example.backend.chat.controller.dto;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageSendDto {
    private Long memberId;
    private String content;
    private String chatRoomId;
    private Date sendTime;

    public ChatMessageSendDto(ChatRoom.LastMessage message, String chatRoomId) {
        this.memberId = message.getSenderId();
        this.content = message.getContent();
        this.sendTime = message.getSendTime();
        this.chatRoomId = chatRoomId;
    }
}
