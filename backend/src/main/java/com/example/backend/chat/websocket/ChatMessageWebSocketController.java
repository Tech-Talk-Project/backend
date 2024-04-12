package com.example.backend.chat.websocket;

import com.example.backend.chat.dto.websocket.ChatMessageReceiveDto;
import com.example.backend.chat.service.ChatPublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageWebSocketController {
    private final ChatPublishService chatPublishService;

    @MessageMapping("/chat/message/{chatRoomId}")
    public void send(ChatMessageReceiveDto chatDto, @DestinationVariable String chatRoomId) {
        chatPublishService.publishMessage(chatRoomId, chatDto);
    }
}
