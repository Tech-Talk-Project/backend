package com.example.backend.chat.controller.dto.response;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

import java.util.List;

@Data
public class ChatMessageListDto {
    List<ChatRoom.LastMessage> messages;

    public ChatMessageListDto(List<ChatRoom.LastMessage> messages) {
        this.messages = messages;
    }
}
