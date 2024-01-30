package com.example.backend.chat.repository;

import com.example.backend.chat.domain.BackupMessages;
import com.example.backend.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BackupMessagesRepository {
    private final MongoTemplate mongoTemplate;

    public void save(BackupMessages message) {
        mongoTemplate.save(message);
    }

    public List<ChatRoom.LastMessage> getChatMessageListAfterCursor(String chatRoomId, Date cursor) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("_id").is(chatRoomId)
                .and("messages.sendTime").lt(cursor));
        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                Aggregation.unwind("messages"),
                Aggregation.sort(Sort.Direction.DESC, "messages.sendTime"),
                Aggregation.limit(100),
                Aggregation.project().and("messages.senderId").as("senderId")
                        .and("messages.sendTime").as("sendTime")
                        .and("messages.content").as("content")
        );

        AggregationResults<ChatRoom.LastMessage> results =
                mongoTemplate.aggregate(aggregation, BackupMessages.class, ChatRoom.LastMessage.class);

        return results.getMappedResults();
    }
}
