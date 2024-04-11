package com.example.backend.chat2.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
public class ChatMember {
    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    private List<JoinedChatRoom> joinedChatRooms = new ArrayList<>();

    @Getter
    public static class JoinedChatRoom {
        private String chatRoomId;
        private LocalDateTime lastAccessTime;

        /**
         * 이 생성자로 생성된 JoinedChatRoom은 lastAccessTime이 현재 시간으로 설정됩니다.
         * @param chatRoomId
         */
        public JoinedChatRoom(String chatRoomId) {
            this.chatRoomId = chatRoomId;
            this.lastAccessTime = LocalDateTime.now();
        }
    }
}
