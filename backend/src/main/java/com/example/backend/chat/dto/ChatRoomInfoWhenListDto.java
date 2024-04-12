package com.example.backend.chat.dto;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

@Data
public class ChatRoomInfoWhenListDto {
    private String chatRoomId;
    private String title;
    private Integer memberCount;
    private Integer unreadCount;
    private Long ownerId;
    private ChatRoom.Message lastMessage;

    public ChatRoomInfoWhenListDto(ChatRoom chatRoom, Integer unreadCount) {
        this.chatRoomId = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.memberCount = chatRoom.getMemberIds().size();
        this.ownerId = chatRoom.getOwnerId();
        this.unreadCount = unreadCount;
        this.lastMessage = chatRoom.getMessages().get(chatRoom.getMessages().size() - 1);
    }
}
