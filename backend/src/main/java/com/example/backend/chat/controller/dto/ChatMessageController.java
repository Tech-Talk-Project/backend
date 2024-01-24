package com.example.backend.chat.controller.dto;

import com.example.backend.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final RabbitTemplate rabbitTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/message/{chatRoomId}")
    public void send(ChatMessageDto chatDto, @DestinationVariable String chatRoomId) {
        chatMessageService.send(chatRoomId, chatDto.getMemberId(), chatDto.getContent());
        rabbitTemplate.convertAndSend("amq.topic", chatRoomId, chatDto);
    }
}
