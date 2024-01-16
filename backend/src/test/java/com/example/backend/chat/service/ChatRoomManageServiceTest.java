package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatMemberRepository;
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
class ChatRoomManageServiceTest {
    @Autowired
    private ChatRoomManageService chatRoomManageService;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @DisplayName("채팅방 생성 테스트")
    @Test
    void createChatRoom() {
        // given
        String title = "테스트 채팅방";
        ChatMember chatMember1 = new ChatMember(1L);
        ChatMember chatMember2 = new ChatMember(2L);
        ChatMember chatMember3 = new ChatMember(3L);
        chatMemberRepository.save(chatMember1);
        chatMemberRepository.save(chatMember2);
        chatMemberRepository.save(chatMember3);

        // when
        ChatRoom chatRoom = chatRoomManageService.createChatRoom(title, List.of(1L, 2L, 3L));

        // then
        // 생성된 채팅방에 초대된 멤버가 전부 참여하고 있는지 확인합니다.
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        assertThat(findChatRoom.getTitle()).isEqualTo(title);
        assertThat(findChatRoom.getJoinedMemberIds()).containsExactly(1L, 2L, 3L);

        // 채팅방에 참여한 멤버들의 joinedChatRooms 에 채팅방 ID 가 추가되었는지 확인합니다.
        ChatMember findChatMember1 = chatMemberRepository.findById(1L);
        assertThat(findChatMember1.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember1.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());

        ChatMember findChatMember2 = chatMemberRepository.findById(2L);
        assertThat(findChatMember2.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember2.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());

        ChatMember findChatMember3 = chatMemberRepository.findById(3L);
        assertThat(findChatMember3.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember3.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());
    }
}