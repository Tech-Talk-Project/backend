package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomInfoWhenListDto;
import com.example.backend.chat.dto.response.ChatRoomListViewDto;
import com.example.backend.chat.dto.response.ChatRoomViewResponseDto;
import com.example.backend.chat.repository.BackupMessageRepository;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomViewService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final BackupMessageRepository backupMessageRepository;
    private final MemberRepository memberRepository;
    public ChatRoomViewResponseDto viewChatRoom(Long memberId, String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        ChatMember chatMember = chatMemberRepository.findById(memberId);

        validateMember(memberId, chatRoom);

        LocalDateTime lastAccessTime = getLastAccessTime(chatMember, chatRoomId);

        List<SimpleMemberProfileDto> simpleProfiles = getSimpleProfile(chatRoom.getMemberIds());


        List<ChatRoom.Message> messages = backupMessageRepository.getMessagesAfterCursor(chatRoomId, lastAccessTime);
        Integer unreadCount = messages.size();
        if (messages.size() < 100) {
            messages = chatRoom.getMessages();
        }
        messages = addUnreadNotification(messages, lastAccessTime);

        return new ChatRoomViewResponseDto(chatRoom, simpleProfiles, messages, unreadCount);
    }

    private List<ChatRoom.Message> addUnreadNotification(List<ChatRoom.Message> messages, LocalDateTime lastAccessTime) {
        List<ChatRoom.Message> result = new ArrayList<>();
        boolean ck = true;
        for (ChatRoom.Message message : messages) {
            if (message.getSendTime().isAfter(lastAccessTime) && ck) {
                ck = false;
                result.add(new ChatRoom.Message(AdminId.TEMP.getValue(), "여기까지 읽었습니다."));
            }
            result.add(message);
        }
        return result;
    }

    private List<SimpleMemberProfileDto> getSimpleProfile(List<Long> memberIds) {
        return memberRepository.findByIdIn(memberIds).stream()
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

    public ChatRoomListViewDto viewChatRoomList(Long memberId) {
        List<ChatRoomInfoWhenListDto> chatRoomList = new ArrayList<>();
        ChatMember chatMember = chatMemberRepository.findById(memberId);

        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            ChatRoom chatRoom = chatRoomRepository.findById(joinedChatRoom.getChatRoomId());
            Integer unreadCount = countUnread(chatRoom.getMessages(), joinedChatRoom.getLastAccessTime());
            chatRoomList.add(new ChatRoomInfoWhenListDto(chatRoom, unreadCount));
        }
        sortChatRoomListByLastMessageTime(chatRoomList);

        return new ChatRoomListViewDto(chatRoomList);
    }

    private void sortChatRoomListByLastMessageTime(List<ChatRoomInfoWhenListDto> chatRoomList) {
        chatRoomList.sort((o1, o2) -> o2.getLastMessage().getSendTime().compareTo(o1.getLastMessage().getSendTime()));
    }

    private Integer countUnread(List<ChatRoom.Message> messages, LocalDateTime lastAccessTime) {
        // message 는 sendTIme 으로 정렬되어 있습니다.
        // 데이터가 많아지면 이진 탐색을 사용하여 성능을 향상시킬 수 있습니다.
        return (int) messages.stream()
                .filter(message -> message.getSendTime().isAfter(lastAccessTime))
                .count();
    }
}
