package com.example.backend.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
@Getter
public class ChatRoom {
    @Id
    private String id;
    private String title;
    private Long ownerId;
    private List<Long> joinedMemberIds = new ArrayList<>();

    // 100개 까지만 저장
    private List<LastMessage> lastMessages = new ArrayList<>();

    public ChatRoom(String title, Long ownerId, List<Long> joinedMemberIds) {
        this.title = title;
        this.ownerId = ownerId;
        this.joinedMemberIds = joinedMemberIds;
    }

    @Getter
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    public static class LastMessage {
        private Long senderId;
        private Date sendTime;
        private String content;

        public LastMessage(Long senderId, Date sendTime, String content) {
            this.senderId = senderId;
            this.sendTime = sendTime;
            this.content = content;
        }
    }
}
