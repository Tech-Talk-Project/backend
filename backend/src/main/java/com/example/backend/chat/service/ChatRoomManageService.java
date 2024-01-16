package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomManageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    public ChatRoom createChatRoom(String title, List<Long> joinedMemberIds) {
        ChatRoom chatRoom = new ChatRoom(title, joinedMemberIds);
        chatRoomRepository.save(chatRoom);

        // 채팅방 생성 후, 채팅방에 참여한 멤버들의 joinedChatRoomIds 에 채팅방 ID 를 추가합니다.
        joinedMemberIds.forEach(memberId -> {
            chatMemberRepository.appendJoinedChatRoomToJoinedChatRooms(
                    memberId,
                    new ChatMember.JoinedChatRoom(chatRoom.getId(), new Date())
            );
        });

        return chatRoom;
    }

    public void addNewMembers(String chatRoomId, List<Long> newMemberIds) {
        chatRoomRepository.appendMemberIdsIntoJoinedMemberIds(chatRoomId, newMemberIds);
        newMemberIds.forEach(memberId -> {
            chatMemberRepository.appendJoinedChatRoomToJoinedChatRooms(
                    memberId,
                    new ChatMember.JoinedChatRoom(chatRoomId, new Date())
            );
        });
    }
}
