package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageListService {
    private final MongoTemplate mongoTemplate;

    public List<ChatRoom.LastMessage> getChatMessageListAfterCursor(String chatRoomId, Date cursor) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("_id").is(chatRoomId)
                .and("backupMessages.sendTime").lt(cursor));
        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                Aggregation.unwind("backupMessages"),
                Aggregation.match(Criteria.where("backupMessages.sendTime").lt(cursor)),
                Aggregation.sort(Sort.Direction.DESC, "backupMessages.sendTime"),
                Aggregation.limit(100),
                Aggregation.project().and("backupMessages.senderId").as("senderId")
                        .and("backupMessages.sendTime").as("sendTime")
                        .and("backupMessages.content").as("content")
        );

        AggregationResults<ChatRoom.LastMessage> results =
                mongoTemplate.aggregate(aggregation, ChatRoom.class, ChatRoom.LastMessage.class);

        return results.getMappedResults();
    }
}
