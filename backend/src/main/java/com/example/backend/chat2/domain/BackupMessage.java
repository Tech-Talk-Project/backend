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
public class BackupMessage {
    @Id
    private String id;

    private List<ChatRoom.Message> messages = new ArrayList<>();
    @CreatedDate
    private LocalDateTime createdAt;

    public BackupMessage(String id) {
        this.id = id;
    }
}
