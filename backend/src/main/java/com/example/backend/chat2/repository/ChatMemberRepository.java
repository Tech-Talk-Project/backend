package com.example.backend.chat2.repository;

import com.example.backend.chat2.domain.ChatMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ChatMemberRepository {
    private final MongoTemplate mongoTemplate;

    public void appendJoinedChatRoom(Long memberId, String chatRoomId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().addToSet("joinedChatRooms", new ChatMember.JoinedChatRoom(chatRoomId));
        mongoTemplate.upsert(query, update, ChatMember.class);
    }

    public void updateLeaveTimeToNow(Long memberId, String chatRoomId) {
        Query query = new Query(Criteria.where("_id").is(memberId)
                .and("joinedChatRooms.chatRoomId").is(chatRoomId));
        Update update = new Update().set("joinedChatRooms.$.lastAccessTime", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, ChatMember.class);
    }

    public void pullJoinedChatRoom(Long memberId, String chatRoomId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().pull("joinedChatRooms", new Query(Criteria.where("chatRoomId").is(chatRoomId)));
        mongoTemplate.updateFirst(query, update, ChatMember.class);
    }

    public ChatMember findById(Long memberId) {
        ChatMember chatMember = mongoTemplate.findById(memberId, ChatMember.class);
        if (chatMember == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        return chatMember;
    }

    public void removeById(Long memberId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        mongoTemplate.remove(query, ChatMember.class);
    }
}
