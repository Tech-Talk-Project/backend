package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.BackupMessagesRepository;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        // 참여자 명단에서 제거
        chatRoom.getJoinedMemberIds().remove(memberId);
        // 참여하고 있는 채팅방에서 제거
        chatMemberRepository.pullJoinedChatRoom(memberId, chatRoomId);


        Integer memberCount = chatRoomRepository.getMemberCount(chatRoomId);
        if (memberCount == 0) {
            chatRoomRepository.delete(chatRoom);
            backupMessagesRepository.deleteById(chatRoomId);
        } else {
            String memberName = memberRepository.findById(memberId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            ).getName();
            chatMessageService.send(chatRoomId, MemberId.LEAVE.getValue(), memberName + "님이 퇴장하셨습니다.");

            if (Objects.equals(chatRoom.getOwnerId(), memberId)) {
                Long newOwnerId = chatRoom.getJoinedMemberIds().get(0);
                String newOwnerName = memberRepository.findById(newOwnerId).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
                ).getName();
                chatRoom.setOwnerId(chatRoom.getJoinedMemberIds().get(0));
                chatRoomRepository.save(chatRoom);

                chatMessageService.send(chatRoomId, MemberId.ADMIN.getValue(), newOwnerName + "님이 방장이 되었습니다.");
            }
        }
    }
}
