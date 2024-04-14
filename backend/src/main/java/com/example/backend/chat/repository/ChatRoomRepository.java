package com.example.backend.chat.repository;

import com.example.backend.chat.domain.ChatRoom;
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

    public void appendMemberId(String chatRoomId, Long memberId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().addToSet("memberIds", memberId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void appendMemberId(ChatRoom chatRoom, Long memberId) {
        chatRoom.getMemberIds().add(memberId);
        Query query = new Query(Criteria.where("_id").is(chatRoom.getId()));
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

    public void appendMessage(ChatRoom chatRoom, ChatRoom.Message message) {
        chatRoom.getMessages().add(message);
        Query query = new Query(Criteria.where("_id").is(chatRoom.getId()));
        Update update = new Update()
                .push("messages")
                .slice(MESSAGE_LIMIT)
                .each(message);
        mongoTemplate.upsert(query, update, ChatRoom.class);
    }

    public void pullMemberId(String chatRoomId, Long memberId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().pull("memberIds", memberId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void pullMemberId(ChatRoom chatRoom, Long memberId) {
        chatRoom.getMemberIds().remove(memberId);
        Query query = new Query(Criteria.where("_id").is(chatRoom.getId()));
        Update update = new Update().pull("memberIds", memberId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public ChatRoom save(ChatRoom chatRoom) {
        mongoTemplate.save(chatRoom);
        return chatRoom;
    }

    public ChatRoom findById(String chatRoomId) {
        ChatRoom chatRoom = mongoTemplate.findById(chatRoomId, ChatRoom.class);
        if (chatRoom == null) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }
        return chatRoom;
    }

    public void remove(ChatRoom chatRoom) {
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

    public Integer getMemberCount(String chatRoomId) {
        ChatRoom chatRoom = findById(chatRoomId);
        return chatRoom.getMemberIds().size();
    }
}
