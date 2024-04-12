package com.example.backend.chat2.dto.response;

import com.example.backend.chat2.domain.ChatRoom;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomViewResponseDto {
    private String title;
    private Long ownerId;
    private List<SimpleMemberProfileDto> members;
    private List<ChatRoom.Message> messages;
    private Integer unreadCount;

    public ChatRoomViewResponseDto(ChatRoom chatRoom, List<SimpleMemberProfileDto> members,
                                   List<ChatRoom.Message> messages, Integer unreadCount) {
        this.title = chatRoom.getTitle();
        this.ownerId = chatRoom.getOwnerId();
        this.members = members;
        this.messages = messages;
        this.unreadCount = unreadCount;
    }
}
