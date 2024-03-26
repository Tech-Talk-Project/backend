package com.example.backend.chat.controller;

import com.example.backend.chat.controller.dto.ChatMessageReceiveDto;
import com.example.backend.chat.controller.dto.ChatMessageSendDto;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageWebSocketController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/message/{chatRoomId}")
    public void send(ChatMessageReceiveDto chatDto, @DestinationVariable String chatRoomId) {
        chatMessageService.send(chatRoomId, chatDto.getMemberId(), chatDto.getContent());
    }
}
