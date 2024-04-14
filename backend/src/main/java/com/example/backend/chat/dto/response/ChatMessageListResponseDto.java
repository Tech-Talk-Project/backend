package com.example.backend.chat.dto.response;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

import java.util.List;

@Data
public class ChatMessageListResponseDto {
    List<ChatRoom.Message> messages;
    public ChatMessageListResponseDto(List<ChatRoom.Message> messages) {
        this.messages = messages;
    }
}
