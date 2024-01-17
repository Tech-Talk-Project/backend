package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRoomListServiceTest {
    @Autowired
    ChatMemberService chatMemberService;

    @Autowired
    ChatRoomManageService chatRoomManageService;

    @Autowired
    ChatRoomListService chatRoomListService;

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
}