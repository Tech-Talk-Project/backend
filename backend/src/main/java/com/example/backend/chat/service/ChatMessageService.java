package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMessage;
import com.example.backend.chat.dto.ChatMessageDto;
import com.example.backend.chat.repository.ChatMessageRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void send(String chatRoomId, Long senderId, String message) {
        ChatMessage chatMessage = new ChatMessage(chatRoomId, senderId, message);
        chatMessageRepository.save(chatMessage);

        ChatMessageDto chatMessageDto = new ChatMessageDto(chatMessage);
        chatRoomRepository.appendChatMessageDtoIntoLastMessages(chatRoomId, chatMessageDto);
    }
}
