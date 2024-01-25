package com.example.backend.chat.controller.dto.response;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomResponseDto {
    private String title;
    private List<SimpleMemberProfileDto> members;
    private List<ChatRoom.LastMessage> messages;

    public ChatRoomResponseDto(String title, List<SimpleMemberProfileDto> members, List<ChatRoom.LastMessage> messages) {
        this.title = title;
        this.members = members;
        this.messages = messages;
    }
}
