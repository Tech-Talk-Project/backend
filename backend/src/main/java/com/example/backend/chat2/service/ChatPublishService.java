package com.example.backend.chat2.service;

import com.example.backend.chat2.domain.ChatRoom;
import com.example.backend.chat2.dto.ChatMessagePublishInfoDto;
import com.example.backend.chat2.dto.ChatRoomInfoWhenListDto;
import com.example.backend.chat2.dto.websocket.ChatMessageReceiveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatPublishService {
    private final RabbitTemplate rabbitTemplate;
    private final String EXCHANGE = "amq.topic";

    public void publishMessage(String topic, ChatMessageReceiveDto dto) {
        rabbitTemplate.convertAndSend(EXCHANGE, topic, new ChatMessagePublishInfoDto(dto));
    }

    public void publishCreateNotification(String topic, ChatRoom createdChatRoom) {
        rabbitTemplate.convertAndSend(
                EXCHANGE, topic, new ChatRoomInfoWhenListDto(createdChatRoom, createdChatRoom.getMessages().size())
        );
    }

    public void publishInviteNotification(String topic, ChatRoom invitedChatRoom, LocalDateTime inviteTime) {
        rabbitTemplate.convertAndSend(
                EXCHANGE, topic, new ChatRoomInfoWhenListDto(invitedChatRoom, getUnreadCountWhenInvite(invitedChatRoom, inviteTime))
        );

    }

    public Integer getUnreadCountWhenInvite(ChatRoom chatRoom, LocalDateTime inviteTime) {
        return (int) chatRoom.getMessages().stream().filter(message -> message.getSendTime().isAfter(inviteTime)).count();
    }
}
