package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomListService {
    private final ChatMemberRepository chatMemberRepository;
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoomByMemberDto> getChatRoomList(Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        List<ChatRoomByMemberDto> chatRoomByMemberDtoList = new ArrayList<>();

        // 마지막 접속 날짜가 가장 최근인 채팅방부터 정렬
        chatMember.getJoinedChatRooms()
                .sort(Comparator.comparing(ChatMember.JoinedChatRoom::getLastAccessTime, Comparator.reverseOrder()));

        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            ChatRoom chatRoom = chatRoomRepository.findById(joinedChatRoom.getChatRoomId());
            Date lastAccessTime = joinedChatRoom.getLastAccessTime();
            chatRoomByMemberDtoList.add(new ChatRoomByMemberDto(chatRoom, lastAccessTime));
        }


        return chatRoomByMemberDtoList;
    }


}
