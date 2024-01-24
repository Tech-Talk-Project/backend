package com.example.backend.chat.dto;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChatRoomByMemberDto {
    private String chatRoomId;
    private String title;
    private List<SimpleMemberProfileDto> joinedMembers;
    private Integer unreadCount;
    private ChatRoom.LastMessage lastMessage;


    public ChatRoomByMemberDto(ChatRoom chatRoom, Integer unreadCount, List<SimpleMemberProfileDto> joinedMembers) {
        this.chatRoomId = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.joinedMembers = joinedMembers;
        this.unreadCount = unreadCount;
        this.lastMessage = chatRoom.getLastMessages().get(chatRoom.getLastMessages().size() - 1);
    }
}
