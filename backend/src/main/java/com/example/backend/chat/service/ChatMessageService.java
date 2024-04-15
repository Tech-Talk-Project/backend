package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.response.ChatMessageListResponseDto;
import com.example.backend.chat.repository.BackupMessageRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.chat.validation.ChatMemberValidator;
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
        if (cursor == null) {
            cursor = LocalDateTime.now();
        }
        chatMemberValidator.validateMember(chatRoomId, memberId);
        List<ChatRoom.Message> messages =
                backupMessageRepository.getMessagesBeforeCursor(chatRoomId, cursor);
        return new ChatMessageListResponseDto(messages);
    }

}
