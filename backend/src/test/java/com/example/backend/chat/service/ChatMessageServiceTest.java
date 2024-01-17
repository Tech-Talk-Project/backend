package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatRoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatMessageServiceTest {
    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomManageService chatRoomManageService;

    @Autowired
    private ChatMemberService chatMemberService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;



    @DisplayName("send 메소드 테스트")
    @Test
    void send() {
        // given
        chatMemberService.createChatMember(1L);
        ChatRoom chatRoom = chatRoomManageService.createChatRoom("test", List.of(1L));


        // when
        chatMessageService.send(chatRoom.getId(), 1L, "hello");
        chatMessageService.send(chatRoom.getId(), 1L, "world");

        // then
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        assertThat(findChatRoom.getLastMessages().size()).isEqualTo(2);
    }
}