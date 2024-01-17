package com.example.backend.chat.repository;

import com.example.backend.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
    private final MongoTemplate mongoTemplate;

    public void save(ChatMessage chatMessage) {
        mongoTemplate.save(chatMessage);
    }


}
