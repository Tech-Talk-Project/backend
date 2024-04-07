package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.exception.MemberAlreadyInvitedException;
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
    private final ChatMemberRepository chatMemberRepository;
    private final MemberRepository memberRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ChatMessageService chatMessageService;

    public ChatRoom createChatRoom(String title, Long roomOwnerId, List<Long> joinedMemberIds) {
        joinedMemberIds.add(roomOwnerId);
        List<Long> joinedMemberIdsWithoutDuplicate = new HashSet<>(joinedMemberIds).stream().toList();
        if (isNoTitle(title)) {
            title = createAutoTitle(joinedMemberIdsWithoutDuplicate);
        }
        ChatRoom chatRoom = new ChatRoom(title, roomOwnerId, joinedMemberIdsWithoutDuplicate);
        chatRoomRepository.save(chatRoom);

        addWelcomeMessage(chatRoom, joinedMemberIdsWithoutDuplicate);

        joinedMemberIdsWithoutDuplicate.forEach(memberId -> {
            ChatMember.JoinedChatRoom joinedChatRoom = new ChatMember.JoinedChatRoom(chatRoom.getId(), new Date());
            chatMemberRepository.appendJoinedChatRoom(memberId, joinedChatRoom);

            publishChatRoomInvitedNotification(chatRoom, memberId);
        });
        return chatRoom;
    }

    private void addWelcomeMessage(ChatRoom chatRoom, List<Long> joinedMemberIds) {
        ChatRoom.LastMessage welcomeMessage =
                new ChatRoom.LastMessage(MemberId.ADMIN.getValue(), new Date(), makeWelcomeMessage(joinedMemberIds));
        chatRoom.getLastMessages().add(welcomeMessage);
        chatRoomRepository.appendMessage(chatRoom.getId(), welcomeMessage);
    }

    private void publishChatRoomInvitedNotification(ChatRoom chatRoom, Long memberId) {
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

    public void inviteChatRoom(String chatRoomId, Long inviteMemberId) {
        // memberId 가 존재하지 않을 때 예외 처리
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        if (chatRoom.getJoinedMemberIds().contains(inviteMemberId)) {
            throw new MemberAlreadyInvitedException("이미 참여한 채팅방입니다.");
        }

        chatRoomRepository.appendMemberIdsIntoJoinedMemberIds(chatRoomId, List.of(inviteMemberId));
        chatMemberRepository.appendJoinedChatRoom(
                inviteMemberId,
                new ChatMember.JoinedChatRoom(chatRoomId, new Date())
        );
        publishChatRoomInvitedNotification(chatRoom, inviteMemberId);
        ChatRoom.LastMessage invitedMessage =
                new ChatRoom.LastMessage(MemberId.ADMIN.getValue(), new Date(), findMemberName(inviteMemberId) + " 님이 초대되었습니다.");
        addInvitedMessage(chatRoom, invitedMessage);
        chatMessageService.send(chatRoomId, MemberId.ADMIN.getValue(), invitedMessage.getContent());
    }

    private void addInvitedMessage(ChatRoom chatRoom, ChatRoom.LastMessage invitedMessage) {
        chatRoomRepository.appendMessage(chatRoom.getId(), invitedMessage);
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

    public void changeTitle(Long memberId, String chatRoomId, String title) {
        validateChatRoomOwner(memberId, chatRoomId);
        chatRoomRepository.changeTitle(chatRoomId, title);
    }

    public void validateChatRoomOwner(Long memberId, String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        if (!chatRoom.getOwnerId().equals(memberId)) {
            throw new IllegalArgumentException("채팅방의 소유자가 아닙니다.");
        }
    }
}
