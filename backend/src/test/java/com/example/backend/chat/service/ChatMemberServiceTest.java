package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ChatMemberServiceTest {
    @Autowired
    private ChatMemberService chatMemberService;

    @Autowired
    private ChatRoomManageService chatRoomManageService;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @DisplayName("채팅방에서 탈퇴하기 테스트")
    @Test
    void exitChatRoomTest() {
        // given
        String title = "테스트 채팅방";
        ChatMember chatMember1 = new ChatMember(1L);
        ChatMember chatMember2 = new ChatMember(2L);
        ChatMember chatMember3 = new ChatMember(3L);
        chatMemberRepository.save(chatMember1);
        chatMemberRepository.save(chatMember2);
        chatMemberRepository.save(chatMember3);

        ChatRoom chatRoom = chatRoomManageService.createChatRoom(title, List.of(1L, 2L, 3L));

        // when
        chatMemberService.exitChatRoom(chatRoom.getId(), 1L);

        // then
        // 채팅방에서 탈퇴한 멤버의 joinedChatRooms 에서 채팅방 ID 가 삭제되었는지 확인합니다.
        ChatMember findChatMember1 = chatMemberRepository.findById(1L);
        assertThat(findChatMember1.getJoinedChatRooms().size()).isEqualTo(0);

        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        assertThat(findChatRoom.getJoinedMemberIds()).containsExactly(2L, 3L);
    }

    @DisplayName("채팅방 떠나기(끄기) 테스트")
    @Test
    void leaveChatRoomTest() {
        // given
        String title = "테스트 채팅방";
        ChatMember chatMember1 = new ChatMember(1L);

        chatMemberRepository.save(chatMember1);

        ChatRoom chatRoom = chatRoomManageService.createChatRoom(title, List.of(1L));

        // when
        Date beforeLeaveTime = chatMemberRepository.findById(1L).getJoinedChatRooms().get(0).getLastAccessTime();
        chatMemberService.leaveChatRoom(1L, chatRoom.getId());

        // then
        chatMemberRepository.findById(1L).getJoinedChatRooms().forEach(joinedChatRoom -> {
            if (joinedChatRoom.getChatRoomId().equals(chatRoom.getId())) {
                assertThat(joinedChatRoom.getLastAccessTime()).isAfter(beforeLeaveTime);
            }
        });
    }
}