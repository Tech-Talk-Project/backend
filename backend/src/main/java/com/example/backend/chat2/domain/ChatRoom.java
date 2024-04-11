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
public class ChatRoom {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdAt;
    private String title;
    private Long ownerId;
    private List<Long> memberIds = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    @Getter
    public static class Message {
        private Long senderId;
        private LocalDateTime sendTime;
        private String content;
    }
}
