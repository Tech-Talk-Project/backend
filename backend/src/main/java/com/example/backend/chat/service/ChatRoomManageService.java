package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.dto.request.ChatRoomTitleUpdateRequestDto;
import com.example.backend.chat.exception.MemberAlreadyInvitedException;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.request.ChatRoomCreateRequestDto;
import com.example.backend.chat.dto.request.ChatRoomInviteRequestDto;
import com.example.backend.chat.dto.request.ChatRoomLeaveRequestDto;
import com.example.backend.chat.repository.BackupMessageRepository;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.repository.member.MemberQuerydsl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomManageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final BackupMessageRepository backupMessagesRepository;
    private final MemberQuerydsl memberQuerydsl;
    private final ChatPublishService chatPublishService;

    public String createChatRoom(Long ownerId, ChatRoomCreateRequestDto chatRoomCreateRequestDto) {
        chatRoomCreateRequestDto.getMemberIds().add(ownerId);
        List<Long> memberIds = getJoinMemberIdsWithoutDuplicate(chatRoomCreateRequestDto);
        String title = createAutoTitleIfNone(chatRoomCreateRequestDto.getTitle(), memberIds);

        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(title, ownerId, memberIds));

        appendJoinedChatRoom(memberIds, chatRoom.getId());
        addWelcomeMessage(chatRoom, memberIds);
        for (Long memberId : memberIds) {
            publishChatRoomCreateNotification(chatRoom, memberId);
        }

        return chatRoom.getId();
    }

    private void appendJoinedChatRoom(List<Long> memberIds, String chatRoomId) {
        for (Long memberId : memberIds) {
            chatMemberRepository.appendJoinedChatRoom(memberId, chatRoomId);
        }
    }

    private void publishChatRoomCreateNotification(ChatRoom chatRoom, Long memberId) {
        String notificationTopic = memberId.toString();
        chatPublishService.publishCreateNotification(notificationTopic, chatRoom);
    }

    private void publishChatRoomInviteNotification(ChatRoom chatRoom, Long memberId, LocalDateTime inviteTime) {
        String notificationTopic = memberId.toString();
        chatPublishService.publishInviteNotification(notificationTopic, chatRoom, inviteTime);
    }

    private void addWelcomeMessage(ChatRoom chatRoom, List<Long> memberIds) {
        String content = concatNames(memberIds) + " 님이 입장하셨습니다.";
        ChatRoom.Message welcomeMessage = new ChatRoom.Message(AdminId.WELCOME.getValue(), content);
        chatRoom.getMessages().add(welcomeMessage);
        chatRoomRepository.appendMessage(chatRoom.getId(), welcomeMessage);
        backupMessagesRepository.appendMessage(chatRoom.getId(), welcomeMessage);
    }

    private String concatNames(List<Long> memberIds) {
        StringBuilder content = new StringBuilder();
        for (int i=0;i<memberIds.size();i++) {
            content.append(memberQuerydsl.findNameById(memberIds.get(i)));
            if (i < memberIds.size() - 1) {
                content.append(", ");
            }
        }
        return content.toString();
    }

    private String createAutoTitleIfNone(String title, List<Long> memberIds) {
        if (title == null || title.isEmpty()) {
            return concatNames(memberIds);
        } else {
            return title;
        }
    }

    private List<Long> getJoinMemberIdsWithoutDuplicate(ChatRoomCreateRequestDto dto) {
        List<Long> memberIds = dto.getMemberIds();
        return new HashSet<>(memberIds).stream().toList();
    }

    public void inviteMember(ChatRoomInviteRequestDto dto) {
        String chatRoomId = dto.getChatRoomId();
        Long memberId = dto.getMemberId();

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        validateInviteMemberId(chatRoom, memberId);
        chatRoomRepository.appendMemberId(chatRoomId, memberId);
        ChatMember.JoinedChatRoom joinedChatRoom = chatMemberRepository.appendJoinedChatRoom(memberId, chatRoomId);
        addInviteMessage(chatRoom, memberId);
        publishChatRoomInviteNotification(chatRoom, memberId, joinedChatRoom.getLastAccessTime());
    }

    private void validateInviteMemberId(ChatRoom chatRoom, Long memberId) {
        if (!memberQuerydsl.existsByMemberId(memberId)) {
            throw new IllegalArgumentException("존재하지 않는 멤버입니다.");
        }
        if (chatRoom.getMemberIds().contains(memberId)) {
            throw new MemberAlreadyInvitedException("이미 참여한 채팅방입니다.");
        }
    }

    private void addInviteMessage(ChatRoom chatRoom, Long memberId) {
        String content = memberQuerydsl.findNameById(memberId) + " 님이 초대되었습니다.";
        ChatRoom.Message inviteMessage = new ChatRoom.Message(AdminId.INVITE.getValue(), content);
        chatRoom.getMessages().add(inviteMessage);
        chatRoomRepository.appendMessage(chatRoom.getId(), inviteMessage);
        backupMessagesRepository.appendMessage(chatRoom.getId(), inviteMessage);
    }

    public void leaveChatRoom(Long memberId, ChatRoomLeaveRequestDto dto) {
        String chatRoomId = dto.getChatRoomId();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        chatRoom.getMemberIds().remove(memberId);
        chatRoomRepository.pullMemberIdFromMemberIds(chatRoomId, memberId);
        chatMemberRepository.pullJoinedChatRoom(memberId, chatRoomId);

        // 채팅방이 비어 있으면 삭제합니다.
        if (chatRoom.getMemberIds().isEmpty()) {
            chatRoomRepository.remove(chatRoom);
            backupMessagesRepository.removeById(chatRoomId);
            return;
        }

        addLeaveMessage(chatRoomId, memberId);

        // 방장이 나가면 새로운 방장 지정
        if (chatRoom.getOwnerId().equals(memberId)) {
            Long newOwnerId = chatRoom.getMemberIds().get(0);
            chatRoomRepository.updateOwnerId(chatRoom.getId(), newOwnerId);
            // 웹소켓 전송 메세지 사이의 시간 간격이 너무 짧으면 프론트에서 데이터를 받지 못하는 경우가 있어서 0.5초 대기
            waitHalfSecond();
            addNewOwnerNotificationMessage(chatRoom.getId(), newOwnerId);
        }
    }

    private void addLeaveMessage(String chatRoomId, Long memberId) {
        String memberName = memberQuerydsl.findNameById(memberId);
        String content = memberName + "님이 퇴장하셨습니다.";
        ChatRoom.Message leaveMessage = new ChatRoom.Message(AdminId.LEAVE.getValue(), content);
        chatRoomRepository.appendMessage(chatRoomId, leaveMessage);
        backupMessagesRepository.appendMessage(chatRoomId, leaveMessage);
    }

    private void addNewOwnerNotificationMessage(String chatRoomId, Long newOwnerId) {
        String newOwnerName = memberQuerydsl.findNameById(newOwnerId);
        String content = newOwnerName + "님이 방장이 되었습니다.";
        ChatRoom.Message newOwnerMessage = new ChatRoom.Message(AdminId.NEW_OWNER.getValue(), content);
        chatRoomRepository.appendMessage(chatRoomId, newOwnerMessage);
        backupMessagesRepository.appendMessage(chatRoomId, newOwnerMessage);
    }

    private void waitHalfSecond() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void changeTitle(Long memberId, ChatRoomTitleUpdateRequestDto dto) {
        String chatRoomId = dto.getChatRoomId();
        String title = dto.getNewTitle();

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        validateChatRoomOwner(memberId, chatRoom);
        chatRoomRepository.updateTitle(chatRoomId, title);
    }

    public void validateChatRoomOwner(Long memberId, ChatRoom chatRoom) {
        if (!chatRoom.getOwnerId().equals(memberId)) {
            throw new IllegalArgumentException("채팅방의 방장이 아닙니다.");
        }
    }
}
