package com.example.backend.chat.websocket;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.websocket.ChatMessageReceiveDto;
import com.example.backend.chat.repository.BackupMessageRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.chat.service.ChatPublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageWebSocketController {
    private final ChatPublishService chatPublishService;
    private final ChatRoomRepository chatRoomRepository;
    private final BackupMessageRepository backupMessageRepository;


    @MessageMapping("/chat/message/{chatRoomId}")
    public void send(ChatMessageReceiveDto chatDto, @DestinationVariable String chatRoomId) {
        ChatRoom.Message message = new ChatRoom.Message(chatDto);
        chatRoomRepository.appendMessage(chatRoomId, message);
        backupMessageRepository.appendMessage(chatRoomId, message);
        chatPublishService.publishMessage(chatRoomId, message);
    }
}
