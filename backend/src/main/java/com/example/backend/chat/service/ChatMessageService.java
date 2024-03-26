package com.example.backend.chat.service;

import com.example.backend.chat.controller.dto.ChatMessageSendDto;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final RabbitTemplate rabbitTemplate;

    public void send(String chatRoomId, Long senderId, String message) {
        ChatRoom.LastMessage lastMessage = new ChatRoom.LastMessage(senderId, new Date(), message);
        chatRoomRepository.appendMessage(chatRoomId, lastMessage);
        rabbitTemplate.convertAndSend("amq.topic", chatRoomId, new ChatMessageSendDto(lastMessage));
    }
}
