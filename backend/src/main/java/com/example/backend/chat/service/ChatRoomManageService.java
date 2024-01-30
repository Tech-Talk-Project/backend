package com.example.backend.chat.service;

import com.example.backend.chat.domain.BackupMessages;
import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.repository.BackupMessagesRepository;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomManageService {
    private final ChatRoomRepository chatRoomRepository;
    private final BackupMessagesRepository backupMessagesRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final MemberRepository memberRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Long ADMIN_ID = -1L;

    public ChatRoom createChatRoom(String title, List<Long> joinedMemberIds) {
        List<Long> joinedMemberIdsWithoutDuplicate = new HashSet<>(joinedMemberIds).stream().toList();
        if (title == null || title.isEmpty()) {
            title = createAutoTitle(joinedMemberIdsWithoutDuplicate);
        }
        ChatRoom chatRoom = new ChatRoom(title, joinedMemberIdsWithoutDuplicate);
        ChatRoom.LastMessage autoFirstMessage =
                new ChatRoom.LastMessage(ADMIN_ID, new Date(), getFirstMessageForMemberName(joinedMemberIdsWithoutDuplicate));
        chatRoom.getLastMessages().add(autoFirstMessage);
        chatRoomRepository.save(chatRoom);

        BackupMessages backupMessages = new BackupMessages(chatRoom.getId());
        backupMessages.getMessages().add(autoFirstMessage);
        backupMessagesRepository.save(backupMessages);

        // 채팅방 생성 후, 채팅방에 참여한 멤버들의 joinedChatRoomIds 에 채팅방 ID 를 추가합니다.
        // 채팅방이 생성되었다는 사실을 memberId topic 으로 구독된 사람들에게 알려줍니다.
        joinedMemberIdsWithoutDuplicate.forEach(memberId -> {
            chatMemberRepository.appendJoinedChatRoomToJoinedChatRooms(
                    memberId,
                    new ChatMember.JoinedChatRoom(chatRoom.getId(), new Date())
            );
            ChatRoomByMemberDto chatRoomDto = new ChatRoomByMemberDto(chatRoom, joinedMemberIdsWithoutDuplicate.size(), 0);
            rabbitTemplate.convertAndSend("amq.topic", memberId.toString(), chatRoomDto);
        });
        return chatRoom;
    }

    private String createAutoTitle(List<Long> members) {
        StringBuilder title = new StringBuilder();
        for (Long memberId : members) {
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 멤버입니다.")
            );
            title.append(member.getName());
            title.append(", ");
        }
        title.delete(title.length() - 2, title.length());
        return title.toString();
    }

    private String getFirstMessageForMemberName(List<Long> joinedMemberIds) {
        StringBuilder firstMessage = new StringBuilder();
        for (Long memberId : joinedMemberIds) {
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 멤버입니다.")
            );
            firstMessage.append(member.getName());
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
