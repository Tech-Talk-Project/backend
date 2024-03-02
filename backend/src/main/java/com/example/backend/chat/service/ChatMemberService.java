package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMemberService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;

    public ChatMember createChatMember(Long memberId) {
        ChatMember chatMember = new ChatMember(memberId);
        chatMemberRepository.save(chatMember);
        return chatMember;
    }

    public void leaveChatRoom(Long memberId, String chatRoomID) {
        chatMemberRepository.updateJoinedChatRoomLeaveTime(memberId, chatRoomID);
    }
    public void exitChatRoom(String chatRoomId, Long memberId) {
        chatRoomRepository.pullMemberIdFromJoinedMemberIds(chatRoomId, memberId);
        chatMemberRepository.pullJoinedChatRoom(memberId, chatRoomId);
    }
}
