package com.example.backend.chat.repository;

import com.example.backend.chat.domain.BackupMessage;
import com.example.backend.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BackupMessageRepository {
    private final MongoTemplate mongoTemplate;

    private static final Integer MESSAGE_LIMIT = 10000;

    public void removeById(String chatRoomId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        mongoTemplate.remove(query, BackupMessage.class);
    }

    public void appendMessage(String chatRoomId, ChatRoom.Message message) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update()
                .push("messages")
                .slice(MESSAGE_LIMIT)
                .each(message);
        mongoTemplate.upsert(query, update, BackupMessage.class);
    }

    public List<ChatRoom.Message> getMessagesAfterCursor(String chatRoomId, LocalDateTime cursor) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("_id").is(chatRoomId));

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                Aggregation.unwind("messages"),
                Aggregation.match(Criteria.where("messages.sendTime").lt(cursor)),
                Aggregation.sort(Sort.Direction.ASC, "messages.sendTime"),
                Aggregation.project().and("messages.senderId").as("senderId")
                        .and("messages.sendTime").as("sendTime")
                        .and("messages.content").as("content")
        );

        return mongoTemplate
                .aggregate(aggregation, BackupMessage.class, ChatRoom.Message.class)
                .getMappedResults();
    }

    public List<ChatRoom.Message> getMessagesBeforeCursor(String chatRoomId, LocalDateTime cursor) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("_id").is(chatRoomId));

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                Aggregation.unwind("messages"),
                Aggregation.match(Criteria.where("messages.sendTime").gt(cursor)),
                Aggregation.sort(Sort.Direction.DESC, "messages.sendTime"),
                Aggregation.limit(MESSAGE_LIMIT),
                Aggregation.project().and("messages.senderId").as("senderId")
                        .and("messages.sendTime").as("sendTime")
                        .and("messages.content").as("content")
        );

        return mongoTemplate
                .aggregate(aggregation, BackupMessage.class, ChatRoom.Message.class)
                .getMappedResults();
    }
}
