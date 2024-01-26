package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomValidator chatRoomValidator;

    public List<ChatRoom.LastMessage> getLastMessages(String chatRoomId) {
        return chatRoomRepository.getLastMessages(chatRoomId);
    }

    public List<ChatRoom.LastMessage> getChatMessageListAfterCursor(Long memberId, String chatRoomId, Date cursor) {
        if (!chatRoomValidator.isMemberOfChatRoom(chatRoomId, memberId)) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }
        return chatRoomRepository.getChatMessageListAfterCursor(chatRoomId, cursor);
    }
}
