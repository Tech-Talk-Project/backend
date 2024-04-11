package com.example.backend.chat2.dto.response;

import lombok.Data;

@Data
public class ChatRoomCreateResponseDto {
    private String chatRoomId;

    public ChatRoomCreateResponseDto(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
