package com.example.backend.chat2.service;

import com.example.backend.chat2.domain.ChatRoom;
import com.example.backend.chat2.dto.response.ChatMessageListResponseDto;
import com.example.backend.chat2.repository.BackupMessageRepository;
import com.example.backend.chat2.validation.ChatMemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final BackupMessageRepository backupMessageRepository;
    private final ChatMemberValidator chatMemberValidator;

    public ChatMessageListResponseDto getMessagesBeforeCursor(Long memberId, String chatRoomId, LocalDateTime cursor) {
        chatMemberValidator.validateMember(chatRoomId, memberId);
        List<ChatRoom.Message> messages =
                backupMessageRepository.getMessagesBeforeCursor(chatRoomId, cursor);
        return new ChatMessageListResponseDto(messages);
    }


}
