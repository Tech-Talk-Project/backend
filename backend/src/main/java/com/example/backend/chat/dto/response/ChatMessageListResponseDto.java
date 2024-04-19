package com.example.backend.chat.dto.response;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatMessageListResponseDto {
    List<ChatRoom.Message> messages;
    LocalDateTime nextCursor;
    public ChatMessageListResponseDto(List<ChatRoom.Message> messages) {
        this.messages = messages;
        if (!messages.isEmpty()) {
            this.nextCursor = messages.get(0).getSendTime();
        }
    }
}
