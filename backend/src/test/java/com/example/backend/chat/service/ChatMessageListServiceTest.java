package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.entity.member.Member;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.service.MemberCreateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatMessageListServiceTest {
    @Autowired
    private ChatMessageListService chatMessageListService;

    @Autowired
    private MemberCreateService memberCreateService;

    @Autowired
    private ChatRoomManageService chatRoomManageService;

    @Autowired
    private ChatMessageService chatMessageService;

    @DisplayName("채팅 메시지 리스트 조회 서비스 - 채팅방을 만든 직후")
    @Test
    void chatMessageListServiceTest() {
        // given
        Member user = memberCreateService.createUser(
                new UserProfileDto("test", "test@com", "test"),
                OAuth2Provider.GITHUB);
        ChatRoom chatRoom = chatRoomManageService.createChatRoom("test", List.of(user.getId()));
        // when
        List<ChatRoom.LastMessage> chatMessageListAfterCursor = chatMessageListService.getChatMessageListAfterCursor(chatRoom.getId(), new Date());

        // then
        assertThat(chatMessageListAfterCursor.size()).isEqualTo(1);
    }

    @DisplayName("채팅 메시지 리스트 조회 서비스 - 50개의 메세지가 보내진 후")
    @Test
    void chatMessageListServiceTest2() {
        // given
        Member user = memberCreateService.createUser(
                new UserProfileDto("test", "test@com", "test"),
                OAuth2Provider.GITHUB);
        ChatRoom chatRoom = chatRoomManageService.createChatRoom("test", List.of(user.getId()));
        for (int i = 0; i < 50; i++) {
            chatMessageService.send(chatRoom.getId(), user.getId(), "test");
        }
        // when
        List<ChatRoom.LastMessage> chatMessageListAfterCursor = chatMessageListService.getChatMessageListAfterCursor(chatRoom.getId(), new Date());

        // then
        assertThat(chatMessageListAfterCursor.size()).isEqualTo(51);
    }

    @DisplayName("채팅 메시지 리스트 조회 서비스 - 100개 이상 메세지가 보내진 후")
    @Test
    void chatMessageListServiceTest3() {
        // given
        Member user = memberCreateService.createUser(
                new UserProfileDto("test", "test@com", "test"),
                OAuth2Provider.GITHUB);
        ChatRoom chatRoom = chatRoomManageService.createChatRoom("test", List.of(user.getId()));
        for (int i = 0; i < 150; i++) {
            chatMessageService.send(chatRoom.getId(), user.getId(), "test" + i);
        }
        // when
        List<ChatRoom.LastMessage> chatMessageListAfterCursor = chatMessageListService.getChatMessageListAfterCursor(chatRoom.getId(), new Date());

        // then
        assertThat(chatMessageListAfterCursor.size()).isEqualTo(100);
        assertThat(chatMessageListAfterCursor.get(0).getContent()).isEqualTo("test149");
    }
}