package com.example.backend.chat2.service;

import com.example.backend.chat2.repository.ChatMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;

    public void outChatRoom(Long memberId, String chatRoomId) {
        chatMemberRepository.updateLeaveTimeToNow(memberId, chatRoomId);
    }
}
