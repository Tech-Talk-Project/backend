package com.example.backend.chat.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
public class BackupMessages {
    @Id
    private String id;

    // 10,000개 까지 저장
    private List<ChatRoom.LastMessage> messages = new ArrayList<>();

    public BackupMessages(String id) {
        this.id = id;
    }
}
