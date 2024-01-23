package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMessage;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatMessageRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatMessage send(String chatRoomId, Long senderId, String message) {
        ChatMessage chatMessage = new ChatMessage(chatRoomId, senderId, message);
        chatMessageRepository.save(chatMessage);

        ChatRoom.LastMessage lastMessage = new ChatRoom.LastMessage(chatMessage);
        chatRoomRepository.appendLastMessageDtoIntoLastMessages(chatRoomId, lastMessage);
        return chatMessage;
    }
}
