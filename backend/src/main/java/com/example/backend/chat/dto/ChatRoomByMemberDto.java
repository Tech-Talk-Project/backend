package com.example.backend.chat.dto;

import com.example.backend.chat.domain.ChatRoom;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChatRoomByMemberDto {
    private String chatRoomId;
    private String title;
    private Integer joinedMemberCount;
    private Integer unreadCount;
    private List<ChatMessageDto> lastMessages;


    public ChatRoomByMemberDto(ChatRoom chatRoom, Date lastAccessTime) {
        this.chatRoomId = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.joinedMemberCount = chatRoom.getJoinedMemberIds().size();
        this.unreadCount = getUnreadCount(chatRoom.getLastMessages(), lastAccessTime);
        this.lastMessages = chatRoom.getLastMessages();
    }

    private Integer getUnreadCount(List<ChatMessageDto> lastMessages, Date lastReadDate) {
        // binary search - upperbound
        int lo = 0;
        int hi = lastMessages.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (lastMessages.get(mid).getSendTime().after(lastReadDate)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lastMessages.size() - lo;
    }
}
