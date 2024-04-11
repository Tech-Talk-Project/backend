package com.example.backend.chat2.service;

import com.example.backend.chat2.domain.ChatRoom;
import com.example.backend.chat2.dto.ChatMessagePublishInfoDto;
import com.example.backend.chat2.dto.ChatRoomInfoWhenListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatPublishService {
    private final RabbitTemplate rabbitTemplate;
    private final String EXCHANGE = "amq.topic";

    public void publishMessage(String topic, ChatRoom.Message message) {
        rabbitTemplate.convertAndSend(EXCHANGE, topic, new ChatMessagePublishInfoDto(message));
    }

    public void publishInviteNotification(String topic, ChatRoom invitedChatRoom) {
        rabbitTemplate.convertAndSend(EXCHANGE, topic, new ChatRoomInfoWhenListDto(invitedChatRoom, 0));
    }
}
