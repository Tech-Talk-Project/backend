package com.example.backend.controller.user.chat.dto.response;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

@Data
public class ChatRoomCreateResponseDto {
    private String chatRoomId;

    public ChatRoomCreateResponseDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getId();
    }
}
