package com.example.backend.chat.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
public class ChatMessage {
    @Id
    private String id;
    @Indexed
    private String chatRoomId;
    private Long senderId;
    private Date sendTime;
    private String content;

    public ChatMessage(String chatRoomId, Long senderId, String content) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
        this.sendTime = new Date();
    }
}
