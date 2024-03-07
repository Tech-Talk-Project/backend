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

import java.util.ArrayList;
import java.util.Collections;
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
        MatchOperation matchStage = Aggregation.match(Criteria.where("_id").is(chatRoomId));

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                Aggregation.unwind("messages"),
                Aggregation.match(Criteria.where("messages.sendTime").lt(cursor)),
                Aggregation.sort(Sort.Direction.DESC, "messages.sendTime"),
                Aggregation.limit(100),
                Aggregation.project().and("messages.senderId").as("senderId")
                        .and("messages.sendTime").as("sendTime")
                        .and("messages.content").as("content")
        );


        List<ChatRoom.LastMessage> results = mongoTemplate
                                    .aggregate(aggregation, BackupMessages.class, ChatRoom.LastMessage.class)
                                    .getMappedResults();

        return getReverse(results);
    }

    private List<ChatRoom.LastMessage> getReverse(List<ChatRoom.LastMessage> results) {
        List<ChatRoom.LastMessage> reverseResults = new ArrayList<>();
        for (int i = results.size() - 1; i >= 0; i--) {
            reverseResults.add(results.get(i));
        }
        return reverseResults;
    }
}
