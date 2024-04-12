package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatMessagePublishInfoDto;
import com.example.backend.chat.dto.ChatRoomInfoWhenListDto;
import com.example.backend.chat.dto.websocket.ChatMessageReceiveDto;
import com.example.backend.chat.repository.BackupMessageRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatPublishService {
    private final RabbitTemplate rabbitTemplate;
    private final String EXCHANGE = "amq.topic";

    public void publishMessage(String topic, ChatRoom.Message message) {
        rabbitTemplate.convertAndSend(EXCHANGE, topic, new ChatMessagePublishInfoDto(message));
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
