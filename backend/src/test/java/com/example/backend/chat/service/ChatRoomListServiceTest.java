package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.entity.member.Member;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.service.MemberCreateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatRoomListServiceTest {
    @Autowired
    ChatMemberService chatMemberService;

    @Autowired
    ChatRoomManageService chatRoomManageService;

    @Autowired
    ChatRoomListService chatRoomListService;

    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    MemberCreateService memberCreateService;

    @DisplayName("채팅방 목록 조회 - 참여 중인 채팅방을 전부 보여주는지")
    @Test
    void getChatRoomList() {
        // given
        Member user = memberCreateService.createUser(new UserProfileDto(
                "user1", "test1", "test.com"
        ), OAuth2Provider.GITHUB);

        ChatMember chatMember = chatMemberService.createChatMember(user.getId());
        chatRoomManageService.createChatRoom("test1", List.of(user.getId()));
        chatRoomManageService.createChatRoom("test2", List.of(user.getId()));
        chatRoomManageService.createChatRoom("test3", List.of(user.getId()));



        // when
        List<ChatRoomByMemberDto> chatRoomList = chatRoomListService.getChatRoomList(user.getId());

        // then
        assertEquals(3, chatRoomList.size());
    }

    @DisplayName("채팅방 목록 조회 - 마지막 접속 날짜순으로 정렬되는지")
    @Test
    void chatRoomListOrderedLastAccess() {
        // given
        Member user = memberCreateService.createUser(new UserProfileDto(
                "user1", "test1", "test.com"
        ), OAuth2Provider.GITHUB);
        ChatMember chatMember = chatMemberService.createChatMember(user.getId());
        ChatRoom chatRoom1 = chatRoomManageService.createChatRoom("test1", List.of(user.getId()));
        ChatRoom chatRoom2 = chatRoomManageService.createChatRoom("test2", List.of(user.getId()));
        ChatRoom chatRoom3 = chatRoomManageService.createChatRoom("test3", List.of(user.getId()));

        // when
        chatMemberService.leaveChatRoom(user.getId(), chatRoom1.getId());
        chatMemberService.leaveChatRoom(user.getId(), chatRoom2.getId());
        chatMemberService.leaveChatRoom(user.getId(), chatRoom3.getId());

        // then
        List<ChatRoomByMemberDto> chatRoomList = chatRoomListService.getChatRoomList(user.getId());
        assertThat(chatRoomList.get(0).getTitle()).isEqualTo("test3");
        assertThat(chatRoomList.get(1).getTitle()).isEqualTo("test2");
        assertThat(chatRoomList.get(2).getTitle()).isEqualTo("test1");
    }

    @DisplayName("채팅방 목록 조회 - 읽지 않은 메세지 개수 확인")
    @Test
    void chatRoomListUnReadMessageCount() {
        // given
        Member user = memberCreateService.createUser(new UserProfileDto(
                "user1", "test1", "test.com"
        ), OAuth2Provider.GITHUB);
        ChatMember chatMember = chatMemberService.createChatMember(user.getId());
        ChatRoom chatRoom1 = chatRoomManageService.createChatRoom("test1", List.of(user.getId()));
        ChatRoom chatRoom2 = chatRoomManageService.createChatRoom("test2", List.of(user.getId()));
        ChatRoom chatRoom3 = chatRoomManageService.createChatRoom("test3", List.of(user.getId()));

        // when

        // 20개의 메시지가 보내졌고 20를 읽지 않은 경우
        for (int i=0;i<20;i++) {
            chatMessageService.send(chatRoom1.getId(), user.getId(), "hello" + i);
        }

        // 150개의 메세지가 보내졌고 모두 읽지 않은 경우
        for (int i=0;i<150;i++) {
            chatMessageService.send(chatRoom2.getId(), user.getId(), "hello" + i);
        }

        // 30개의 메세지가 보내졌고 20개만 읽은 경우
        for (int i=0;i<20;i++) {
            chatMessageService.send(chatRoom3.getId(), user.getId(), "hello" + i);
        }
        chatMemberService.leaveChatRoom(user.getId(), chatRoom3.getId());
        for (int i=20;i<30;i++) {
            chatMessageService.send(chatRoom3.getId(), user.getId(), "hello" + i);
        }

        // then
        // 순서 : test3, test2, test1
        List<ChatRoomByMemberDto> chatRoomList = chatRoomListService.getChatRoomList(user.getId());
        System.out.println(chatRoomList);
        assertThat(chatRoomList.get(0).getUnreadCount()).isEqualTo(10);
        assertThat(chatRoomList.get(1).getUnreadCount()).isEqualTo(100);
        assertThat(chatRoomList.get(2).getUnreadCount()).isEqualTo(20);
    }


}