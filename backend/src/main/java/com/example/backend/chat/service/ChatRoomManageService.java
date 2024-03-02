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
        if (isNoTitle(title)) {
            title = createAutoTitle(joinedMemberIdsWithoutDuplicate);
        }
        ChatRoom chatRoom = new ChatRoom(title, joinedMemberIdsWithoutDuplicate);
        chatRoomRepository.save(chatRoom);

        addWelcomeMessage(chatRoom, joinedMemberIdsWithoutDuplicate);

        joinedMemberIdsWithoutDuplicate.forEach(memberId -> {
            ChatMember.JoinedChatRoom joinedChatRoom = new ChatMember.JoinedChatRoom(chatRoom.getId(), new Date());
            chatMemberRepository.appendJoinedChatRoom(memberId, joinedChatRoom);

            publishChatRoomCreateNotification(chatRoom, memberId);
        });
        return chatRoom;
    }

    private void addWelcomeMessage(ChatRoom chatRoom, List<Long> joinedMemberIds) {
        ChatRoom.LastMessage welcomeMessage =
                new ChatRoom.LastMessage(ADMIN_ID, new Date(), makeWelcomeMessage(joinedMemberIds));
        chatRoomRepository.appendMessage(chatRoom.getId(), welcomeMessage);
    }

    private void publishChatRoomCreateNotification(ChatRoom chatRoom, Long memberId) {
        ChatRoomByMemberDto chatRoomDto = new ChatRoomByMemberDto(chatRoom, 0);
        rabbitTemplate.convertAndSend("amq.topic", memberId.toString(), chatRoomDto);
    }

    private Boolean isNoTitle(String title) {
        return title == null || title.isEmpty();
    }

    private String findMemberName(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 멤버입니다.")
        );
        return member.getName();
    }

    private String concatMemberNames(List<Long> memberIds) {
        StringBuilder title = new StringBuilder();
        for (Long memberId : memberIds) {
            title.append(findMemberName(memberId));
            title.append(", ");
        }
        title.delete(title.length() - 2, title.length());
        return title.toString();
    }

    private String createAutoTitle(List<Long> members) {
        return concatMemberNames(members);
    }

    private String makeWelcomeMessage(List<Long> joinedMemberIds) {
        return concatMemberNames(joinedMemberIds) + " 님이 입장하셨습니다.";
    }

    public void addNewMembers(String chatRoomId, List<Long> newMemberIds) {
        chatRoomRepository.appendMemberIdsIntoJoinedMemberIds(chatRoomId, newMemberIds);
        newMemberIds.forEach(memberId -> {
            chatMemberRepository.appendJoinedChatRoom(
                    memberId,
                    new ChatMember.JoinedChatRoom(chatRoomId, new Date())
            );
        });
    }
}
