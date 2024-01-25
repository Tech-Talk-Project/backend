package com.example.backend.chat.service;

import com.example.backend.chat.controller.dto.response.ChatRoomResponseDto;
import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.profile.MemberProfileRepository;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomSearchService {
    private final ChatMemberRepository chatMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberProfileRepository memberProfileRepository;

    public ChatRoomResponseDto getChatRoom(Long memberId, String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        List<SimpleMemberProfileDto> simpleMemberProfileDtoList = getJoinedMembers(chatRoom.getJoinedMemberIds());
        List<ChatRoom.LastMessage> lastMessages = chatRoom.getLastMessages();
        return new ChatRoomResponseDto(chatRoom.getTitle(), simpleMemberProfileDtoList, lastMessages);
    }

    public List<ChatRoomByMemberDto> getChatRoomList(Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        List<ChatRoomByMemberDto> chatRoomByMemberDtoList = new ArrayList<>();

        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            ChatRoom chatRoom = chatRoomRepository.findByIdWithoutBackupMessages(joinedChatRoom.getChatRoomId());
            Date lastAccessTime = joinedChatRoom.getLastAccessTime();
            Integer memberCount = chatRoom.getJoinedMemberIds().size();
            Integer unreadCount = getUnreadCount(chatRoom.getLastMessages(), lastAccessTime);
            chatRoomByMemberDtoList.add(
                    new ChatRoomByMemberDto(chatRoom, memberCount, unreadCount));
        }

        chatRoomByMemberDtoList.sort((dto1, dto2) ->
                dto2.getLastMessage().getSendTime().compareTo(dto1.getLastMessage().getSendTime()));

        return chatRoomByMemberDtoList;
    }

    private List<SimpleMemberProfileDto> getJoinedMembers(List<Long> joinedMemberIds) {
        List<SimpleMemberProfileDto> simpleMemberProfileDtoList = new ArrayList<>();
        for (Long memberId : joinedMemberIds) {
            Member member = memberProfileRepository.findByIdWithProfileInfo(memberId);
            simpleMemberProfileDtoList.add(new SimpleMemberProfileDto(member));
        }
        return simpleMemberProfileDtoList;
    }

    private Integer getUnreadCount(List<ChatRoom.LastMessage> lastMessages, Date lastReadDate) {
        // binary search - upperbound
        int lo = 0;
        int hi = lastMessages.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (lastMessages.get(mid).getSendTime().after(lastReadDate)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lastMessages.size() - lo;
    }

}
