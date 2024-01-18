package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomManageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final Long ADMIN_ID = -1L;


    public ChatRoom createChatRoom(String title, List<Long> joinedMemberIds) {
        ChatRoom chatRoom = new ChatRoom(title, joinedMemberIds);
        chatRoom.getLastMessages().add(
                new ChatRoom.LastMessage(
                        ADMIN_ID,
                        new Date(),
                        getFirstMessageForMemberName(joinedMemberIds)
                ));
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

    private String getFirstMessageForMemberName(List<Long> joinedMemberIds) {
        StringBuilder firstMessage = new StringBuilder();
        for (Long memberId : joinedMemberIds) {
            firstMessage.append(memberProfileRepository.findByIdWithProfileInfo(memberId).getProfile().getInfo().getNickname());
            firstMessage.append(", ");
        }
        firstMessage.delete(firstMessage.length() - 2, firstMessage.length());
        firstMessage.append(" 님이 입장하셨습니다.");
        return firstMessage.toString();
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
