package com.example.backend.chat2.repository;

import com.example.backend.chat2.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final MongoTemplate mongoTemplate;
    private static final Integer MESSAGE_LIMIT = 100;

    public void appendMemberIdToMemberIds(String chatRoomId, Long memberId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().addToSet("memberIds", memberId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void appendMessage(String chatRoomId, ChatRoom.Message message) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update()
                .push("messages")
                .slice(MESSAGE_LIMIT)
                .each(message);
        mongoTemplate.upsert(query, update, ChatRoom.class);
    }

    public void pullMemberIdFromMemberIds(String chatRoomId, Long memberId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().pull("memberIds", memberId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void save(ChatRoom chatRoom) {
        mongoTemplate.save(chatRoom);
    }

    public ChatRoom findById(String chatRoomId) {
        ChatRoom chatRoom = mongoTemplate.findById(chatRoomId, ChatRoom.class);
        if (chatRoom == null) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }
        return chatRoom;
    }

    public void delete(ChatRoom chatRoom) {
        mongoTemplate.remove(chatRoom);
    }

    public void updateTitle(String chatRoomId, String title) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().set("title", title);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void updateOwnerId(String chatRoomId, Long ownerId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().set("ownerId", ownerId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }
}
