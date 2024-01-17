package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRoomListServiceTest {
    @Autowired
    ChatMemberService chatMemberService;

    @Autowired
    ChatRoomManageService chatRoomManageService;

    @Autowired
    ChatRoomListService chatRoomListService;

    @Autowired
    ChatMessageService chatMessageService;

    @DisplayName("채팅방 목록 조회 - 참여 중인 채팅방을 전부 보여주는지")
    @Test
    void getChatRoomList() {
        // given
        ChatMember chatMember = chatMemberService.createChatMember(1L);
        chatRoomManageService.createChatRoom("test1", List.of(1L));
        chatRoomManageService.createChatRoom("test2", List.of(1L));
        chatRoomManageService.createChatRoom("test3", List.of(1L));

        // when
        List<ChatRoomByMemberDto> chatRoomList = chatRoomListService.getChatRoomList(1L);

        // then
        assertEquals(3, chatRoomList.size());
    }

    @DisplayName("채팅방 목록 조회 - 마지막 접속 날짜순으로 정렬되는지")
    @Test
    void chatRoomListOrderedLastAccess() {
        // given
        ChatMember chatMember = chatMemberService.createChatMember(1L);
        ChatRoom chatRoom1 = chatRoomManageService.createChatRoom("test1", List.of(1L));
        ChatRoom chatRoom2 = chatRoomManageService.createChatRoom("test2", List.of(1L));
        ChatRoom chatRoom3 = chatRoomManageService.createChatRoom("test3", List.of(1L));

        // when
        chatMemberService.leaveChatRoom(1L, chatRoom1.getId());
        chatMemberService.leaveChatRoom(1L, chatRoom2.getId());
        chatMemberService.leaveChatRoom(1L, chatRoom3.getId());

        // then
        List<ChatRoomByMemberDto> chatRoomList = chatRoomListService.getChatRoomList(1L);
        assertThat(chatRoomList.get(0).getTitle()).isEqualTo("test3");
        assertThat(chatRoomList.get(1).getTitle()).isEqualTo("test2");
        assertThat(chatRoomList.get(2).getTitle()).isEqualTo("test1");
    }

    @DisplayName("채팅방 목록 조회 - 읽지 않은 메세지 개수 확인")
    @Test
    void chatRoomListUnReadMessageCount() {
        // given
        ChatMember chatMember = chatMemberService.createChatMember(1L);
        ChatRoom chatRoom1 = chatRoomManageService.createChatRoom("test1", List.of(1L));
        ChatRoom chatRoom2 = chatRoomManageService.createChatRoom("test2", List.of(1L));
        ChatRoom chatRoom3 = chatRoomManageService.createChatRoom("test3", List.of(1L));

        // when

        // 20개의 메시지가 보내졌고 20를 읽지 않은 경우
        for (int i=0;i<20;i++) {
            chatMessageService.send(chatRoom1.getId(), 1L, "hello" + i);
        }

        // 150개의 메세지가 보내졌고 모두 읽지 않은 경우
        for (int i=0;i<150;i++) {
            chatMessageService.send(chatRoom2.getId(), 1L, "hello" + i);
        }

        // 30개의 메세지가 보내졌고 20개만 읽은 경우
        for (int i=0;i<20;i++) {
            chatMessageService.send(chatRoom3.getId(), 1L, "hello" + i);
        }
        chatMemberService.leaveChatRoom(1L, chatRoom3.getId());
        for (int i=20;i<30;i++) {
            chatMessageService.send(chatRoom3.getId(), 1L, "hello" + i);
        }

        // then
        // 순서 : test3, test2, test1
        List<ChatRoomByMemberDto> chatRoomList = chatRoomListService.getChatRoomList(1L);
        System.out.println(chatRoomList);
        assertThat(chatRoomList.get(0).getUnreadCount()).isEqualTo(10);
        assertThat(chatRoomList.get(1).getUnreadCount()).isEqualTo(100);
        assertThat(chatRoomList.get(2).getUnreadCount()).isEqualTo(20);
    }


}