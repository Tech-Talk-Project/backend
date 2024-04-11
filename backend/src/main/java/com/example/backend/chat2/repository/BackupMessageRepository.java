package com.example.backend.chat2.repository;

import com.example.backend.chat2.domain.BackupMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BackupMessageRepository {
    private final MongoTemplate mongoTemplate;

    public void removeById(String chatRoomId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        mongoTemplate.remove(query, BackupMessage.class);
    }

}
