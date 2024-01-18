package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatMessage;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.entity.member.Member;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.service.MemberCreateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatMemberServiceTest {
    @Autowired
    private ChatMemberService chatMemberService;

    @Autowired
    private ChatRoomManageService chatRoomManageService;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    MemberCreateService memberCreateService;

    @Autowired
    MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(ChatMessage.class);
        mongoTemplate.dropCollection(ChatMember.class);
        mongoTemplate.dropCollection(ChatRoom.class);
    }

    @DisplayName("채팅방에서 탈퇴하기 테스트")
    @Test
    void exitChatRoomTest() {
        // given
        String title = "테스트 채팅방";
        Member user1 = memberCreateService.createUser(new UserProfileDto("test1", "test1@com", "test1"), OAuth2Provider.GITHUB);
        Member user2 = memberCreateService.createUser(new UserProfileDto("test2", "test2@com", "test2"), OAuth2Provider.GITHUB);
        Member user3 = memberCreateService.createUser(new UserProfileDto("test3", "test3@com", "test3"), OAuth2Provider.GITHUB);


        ChatRoom chatRoom = chatRoomManageService.createChatRoom(title, List.of(user1.getId(), user2.getId(), user3.getId()));

        // when
        chatMemberService.exitChatRoom(chatRoom.getId(), user1.getId());

        // then
        // 채팅방에서 탈퇴한 멤버의 joinedChatRooms 에서 채팅방 ID 가 삭제되었는지 확인합니다.
        ChatMember findChatMember1 = chatMemberRepository.findById(user1.getId());
        assertThat(findChatMember1.getJoinedChatRooms().size()).isEqualTo(0);

        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        assertThat(findChatRoom.getJoinedMemberIds()).containsExactly(user2.getId(), user3.getId());
    }

    @DisplayName("채팅방 떠나기(끄기) 테스트")
    @Test
    void leaveChatRoomTest() {
        // given
        String title = "테스트 채팅방";
        Member user = memberCreateService.createUser(new UserProfileDto("test", "test@com", "test"), OAuth2Provider.GITHUB);

        ChatRoom chatRoom = chatRoomManageService.createChatRoom(title, List.of(user.getId()));

        // when
        Date beforeLeaveTime = chatMemberRepository.findById(user.getId()).getJoinedChatRooms().get(0).getLastAccessTime();
        chatMemberService.leaveChatRoom(user.getId(), chatRoom.getId());

        // then
        chatMemberRepository.findById(user.getId()).getJoinedChatRooms().forEach(joinedChatRoom -> {
            if (joinedChatRoom.getChatRoomId().equals(chatRoom.getId())) {
                assertThat(joinedChatRoom.getLastAccessTime()).isAfter(beforeLeaveTime);
            }
        });
    }
}