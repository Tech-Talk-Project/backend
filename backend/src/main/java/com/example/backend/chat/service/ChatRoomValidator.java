package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.ChatMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomValidator {
    private final ChatMemberRepository chatMemberRepository;

    public boolean isMemberOfChatRoom(String chatRoomId, Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        return chatMember.getJoinedChatRooms().stream()
                .anyMatch(joinedChatRoom -> joinedChatRoom.getChatRoomId().equals(chatRoomId));
    }

}
