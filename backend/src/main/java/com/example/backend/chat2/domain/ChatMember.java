package com.example.backend.chat2.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
public class ChatMember {
    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @Getter
    public static class JoinedChatRoom {
        private String chatRoomId;
        private LocalDateTime lastAccessTime;

        public JoinedChatRoom(String chatRoomId, LocalDateTime lastAccessTime) {
            this.chatRoomId = chatRoomId;
            this.lastAccessTime = lastAccessTime;
        }
    }
}
