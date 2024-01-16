package com.example.backend.chat.repository;


import com.example.backend.chat.domain.ChatMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ChatMemberRepository  {
    private final MongoTemplate mongoTemplate;

    public void appendJoinedChatRoomToJoinedChatRooms(Long memberId, ChatMember.JoinedChatRoom joinedChatRoom) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().addToSet("joinedChatRooms", joinedChatRoom);
        mongoTemplate.updateFirst(query, update, ChatMember.class);
    }

    public void save(ChatMember chatMember) {
        mongoTemplate.save(chatMember);
    }
}
