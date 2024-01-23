package com.example.backend.controller.user.chat;

import com.example.backend.chat.domain.ChatMessage;
import com.example.backend.chat.service.ChatMessageService;
import com.example.backend.controller.user.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final RabbitTemplate rabbitTemplate;
    private final ChatMessageService chatMessageService;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing-key.name}")
    private String routingKey;

    @MessageMapping("/chat/message/{chatRoomId}")
    public void send(ChatDto chatDto, @DestinationVariable String chatRoomId) {
        ChatMessage chatMessage = chatMessageService.send(chatRoomId, chatDto.getMemberId(), chatDto.getMessage());
        rabbitTemplate.convertAndSend("amq.topic", chatRoomId, chatDto);
    }

    @GetMapping("/chat/rooms")
    public String getRooms(){
        return "chat/rooms";
    }

    @GetMapping(value = "/chat/room")
    public String getRoom(Long chatRoomId, String nickname, Model model){

        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("nickname", nickname);

        return "chat/room";
    }
}