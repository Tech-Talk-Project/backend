package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.BackupMessagesRepository;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMemberService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageService chatMessageService;
    private final MemberRepository memberRepository;
    private final BackupMessagesRepository backupMessagesRepository;


    public ChatMember createChatMember(Long memberId) {
        ChatMember chatMember = new ChatMember(memberId);
        chatMemberRepository.save(chatMember);
        return chatMember;
    }

    public void leaveChatRoom(Long memberId, String chatRoomId) {
        chatMemberRepository.updateJoinedChatRoomLeaveTime(memberId, chatRoomId);
    }
    public void exitChatRoom(String chatRoomId, Long memberId) {
        chatRoomRepository.pullMemberIdFromJoinedMemberIds(chatRoomId, memberId);
        chatMemberRepository.pullJoinedChatRoom(memberId, chatRoomId);
        String memberName = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        ).getName();

        Integer memberCount = chatRoomRepository.getMemberCount(chatRoomId);
        if (memberCount == 0) {
            chatRoomRepository.deleteById(chatRoomId);
            backupMessagesRepository.deleteById(chatRoomId);
        } else {
            chatMessageService.send(chatRoomId, MemberId.LEAVE.getValue(), memberName + "님이 퇴장하셨습니다.");
        }
    }
}
