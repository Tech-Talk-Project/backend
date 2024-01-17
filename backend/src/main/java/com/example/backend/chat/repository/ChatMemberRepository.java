package com.example.backend.chat.repository;


import com.example.backend.chat.domain.ChatMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
@RequiredArgsConstructor
public class ChatMemberRepository  {
    private final MongoTemplate mongoTemplate;

    public void appendJoinedChatRoomToJoinedChatRooms(Long memberId, ChatMember.JoinedChatRoom joinedChatRoom) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().addToSet("joinedChatRooms", joinedChatRoom);
        mongoTemplate.upsert(query, update, ChatMember.class);
    }

    public void updateJoinedChatRoomLeaveTime(Long memberId, String ChatRoomId) {
        Query query = new Query(Criteria.where("_id").is(memberId)
                .and("joinedChatRooms.chatRoomId").is(ChatRoomId));
        Update update = new Update().set("joinedChatRooms.$.lastAccessTime", new Date());
        mongoTemplate.updateFirst(query, update, ChatMember.class);
    }

    public void pullJoinedChatRoomFromJoinedChatRooms(Long memberId, String chatRoomId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().pull("joinedChatRooms", new Query(Criteria.where("chatRoomId").is(chatRoomId)));
        mongoTemplate.updateFirst(query, update, ChatMember.class);
    }

    public void save(ChatMember chatMember) {
        mongoTemplate.save(chatMember);
    }

    public ChatMember findById(Long memberId) {
        ChatMember chatMember = mongoTemplate.findById(memberId, ChatMember.class);
        if (chatMember == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        return chatMember;
    }
}
