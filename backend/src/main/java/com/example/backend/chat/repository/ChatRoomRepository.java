package com.example.backend.chat.repository;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final MongoTemplate mongoTemplate;

    public void appendMemberIdIntoJoinedMemberIds(String chatRoomID, Long memberId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomID));
        Update update = new Update().addToSet("joinedMemberIds", memberId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void appendMemberIdsIntoJoinedMemberIds(String chatRoomId, List<Long> memberIds) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().addToSet("joinedMemberIds").each(memberIds);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void appendChatMessageDtoIntoLastMessages(String chatRoomId, ChatMessageDto chatMessageDto) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().push("lastMessages")
                .slice(-100)
                .each(chatMessageDto);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void pullMemberIdFromJoinedMemberIds(String chatRoomId, Long memberId) {
        Query query = new Query(Criteria.where("_id").is(chatRoomId));
        Update update = new Update().pull("joinedMemberIds", memberId);
        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void save(ChatRoom chatRoom) {
        mongoTemplate.save(chatRoom);
    }

    public ChatRoom findById(String chatRoomId) {
        ChatRoom chatRoom = mongoTemplate.findById(chatRoomId, ChatRoom.class);
        if (chatRoom == null) {
            throw new IllegalArgumentException("해당 채팅방이 존재하지 않습니다.");
        }
        return chatRoom;
    }
}
