package com.example.backend.chat2.validation;

import com.example.backend.chat2.domain.ChatMember;
import com.example.backend.chat2.domain.ChatRoom;
import com.example.backend.chat2.repository.ChatMemberRepository;
import com.example.backend.chat2.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMemberValidator {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;

    public void validateMember(String chatRoomId, Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        chatRoom.getMemberIds().stream()
                .filter(memberId::equals)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("채팅방에 유저가 존재하지 않습니다."));

        chatMember.getJoinedChatRooms().stream()
                .filter(joinedChatRoom -> joinedChatRoom.getChatRoomId().equals(chatRoomId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유저의 채팅방 리스트에 채팅방이 존재하지 않습니다."));

    }
}
