package com.example.backend.chat2.service;

import com.example.backend.chat2.domain.ChatMember;
import com.example.backend.chat2.domain.ChatRoom;
import com.example.backend.chat2.dto.response.ChatRoomViewResponseDto;
import com.example.backend.chat2.repository.BackupMessageRepository;
import com.example.backend.chat2.repository.ChatMemberRepository;
import com.example.backend.chat2.repository.ChatRoomRepository;
import com.example.backend.repository.member.MemberQuerydsl;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomViewService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final BackupMessageRepository backupMessageRepository;
    private final MemberQuerydsl memberQuerydsl;
    public ChatRoomViewResponseDto viewChatRoom(Long memberId, String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        ChatMember chatMember = chatMemberRepository.findById(memberId);

        validateMember(memberId, chatRoom);

        LocalDateTime lastAccessTime = getLastAccessTime(chatMember, chatRoomId);

        List<SimpleMemberProfileDto> simpleProfiles = getSimpleProfile(chatRoom.getMemberIds());


        List<ChatRoom.Message> messages = backupMessageRepository.getMessagesBeforeCursor(chatRoomId, lastAccessTime);
        Integer unreadCount = messages.size();
        if (messages.size() < 100) {
            messages = chatRoom.getMessages();
        }

        return new ChatRoomViewResponseDto(chatRoom, simpleProfiles, messages, unreadCount);
    }

    private List<SimpleMemberProfileDto> getSimpleProfile(List<Long> memberIds) {
        return memberQuerydsl.findByIdIn(memberIds).stream()
                .map(SimpleMemberProfileDto::new)
                .collect(Collectors.toList());
    }

    private void validateMember(Long memberId, ChatRoom chatRoom) {
        if (!chatRoom.getMemberIds().contains(memberId)) {
            throw new IllegalArgumentException("채팅방에 속해있지 않은 멤버입니다.");
        }
    }

    private LocalDateTime getLastAccessTime(ChatMember chatMember, String chatRoomId) {
        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            if (joinedChatRoom.getChatRoomId().equals(chatRoomId)) {
                return joinedChatRoom.getLastAccessTime();
            }
        }
        throw new IllegalArgumentException("채팅방에서 나간 시간을 찾을 수 없습니다.");
    }
}
