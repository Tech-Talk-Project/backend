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
    private List<Long> joinedMemberIds = new ArrayList<>();

    // 100개까지만 저장하고 그 이전 메세지의 경우 ChatMessage 를 커서 기반으로 페이징합니다.
    private List<LastMessage> lastMessages = new ArrayList<>();

    public ChatRoom(String title, List<Long> joinedMemberIds) {
        this.title = title;
        this.joinedMemberIds = joinedMemberIds;
    }

    @Getter
    @NoArgsConstructor
    public static class LastMessage {
        private Long senderId;
        private Date sendTime;
        private String content;

        public LastMessage(Long senderId, Date sendTime, String content) {
            this.senderId = senderId;
            this.sendTime = sendTime;
            this.content = content;
        }

        public LastMessage(ChatMessage chatMessage) {
            this.senderId = chatMessage.getSenderId();
            this.sendTime = chatMessage.getSendTime();
            this.content = chatMessage.getContent();
        }
    }
}
