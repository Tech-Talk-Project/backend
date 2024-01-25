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
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomManageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final ChatMessageService chatMessageService;
    private final Long ADMIN_ID = -1L;

    public ChatRoom createChatRoom(String title, List<Long> joinedMemberIds) {
        List<Long> joinedMemberIdsWithoutDuplicate = new HashSet<>(joinedMemberIds).stream().toList();
        if (title == null || title.isEmpty()) {
            title = createAutoTitle(joinedMemberIdsWithoutDuplicate);
        }
        ChatRoom chatRoom = new ChatRoom(title, joinedMemberIdsWithoutDuplicate);
        chatRoomRepository.save(chatRoom);

        chatMessageService.send(chatRoom.getId(), ADMIN_ID, getFirstMessageForMemberName(joinedMemberIds));

        // 채팅방 생성 후, 채팅방에 참여한 멤버들의 joinedChatRoomIds 에 채팅방 ID 를 추가합니다.
        joinedMemberIdsWithoutDuplicate.forEach(memberId -> {
            chatMemberRepository.appendJoinedChatRoomToJoinedChatRooms(
                    memberId,
                    new ChatMember.JoinedChatRoom(chatRoom.getId(), new Date())
            );
        });

        return chatRoom;
    }

    private String createAutoTitle(List<Long> members) {
        StringBuilder title = new StringBuilder();
        for (Long memberId : members) {
            title.append(memberProfileRepository.findByIdWithProfileInfo(memberId).getProfile().getInfo().getNickname());
            title.append(", ");
        }
        title.delete(title.length() - 2, title.length());
        return title.toString();
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
