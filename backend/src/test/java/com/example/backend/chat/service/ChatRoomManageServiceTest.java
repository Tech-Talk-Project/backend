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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatRoomManageServiceTest {
    @Autowired
    private ChatRoomManageService chatRoomManageService;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MemberCreateService memberCreateService;

    @Autowired
    MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(ChatMessage.class);
        mongoTemplate.dropCollection(ChatMember.class);
        mongoTemplate.dropCollection(ChatRoom.class);
    }

    @DisplayName("채팅방 생성 테스트")
    @Test
    void createChatRoom() {
        // given
        String title = "테스트 채팅방";
        Member user1 = memberCreateService.createUser(new UserProfileDto("test1", "test1@com", "test1"), OAuth2Provider.GITHUB);
        Member user2 = memberCreateService.createUser(new UserProfileDto("test2", "test2@com", "test2"), OAuth2Provider.GITHUB);
        Member user3 = memberCreateService.createUser(new UserProfileDto("test3", "test3@com", "test3"), OAuth2Provider.GITHUB);

        // when
        ChatRoom chatRoom = chatRoomManageService.createChatRoom(title, List.of(user1.getId(), user2.getId(), user3.getId()));

        // then
        // 생성된 채팅방에 초대된 멤버가 전부 참여하고 있는지 확인합니다.
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        assertThat(findChatRoom.getLastMessages().get(0).getContent())
                .isEqualTo("test1, test2, test3 님이 입장하셨습니다.");
        assertThat(findChatRoom.getTitle()).isEqualTo(title);
        assertThat(findChatRoom.getJoinedMemberIds()).containsExactly(user1.getId(), user2.getId(), user3.getId());

        // 채팅방에 참여한 멤버들의 joinedChatRooms 에 채팅방 ID 가 추가되었는지 확인합니다.
        ChatMember findChatMember1 = chatMemberRepository.findById(user1.getId());
        assertThat(findChatMember1.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember1.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());

        ChatMember findChatMember2 = chatMemberRepository.findById(user2.getId());
        assertThat(findChatMember2.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember2.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());

        ChatMember findChatMember3 = chatMemberRepository.findById(user3.getId());
        assertThat(findChatMember3.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember3.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());
    }

    @DisplayName("채팅방에 새로운 멤버 추가 테스트")
    @Test
    void testNewMemberAppended() {
        // given
        String title = "테스트 채팅방";
        Member user1 = memberCreateService.createUser(new UserProfileDto("test1", "test1@com", "test1"), OAuth2Provider.GITHUB);
        Member user2 = memberCreateService.createUser(new UserProfileDto("test2", "test2@com", "test2"), OAuth2Provider.GITHUB);
        Member user3 = memberCreateService.createUser(new UserProfileDto("test3", "test3@com", "test3"), OAuth2Provider.GITHUB);

        ChatRoom chatRoom = chatRoomManageService.createChatRoom(title, List.of(user1.getId()));

        // when
        chatRoomManageService.addNewMembers(chatRoom.getId(), List.of(user2.getId(), user3.getId()));

        // then
        // 새로운 멤버가 채팅방에 추가되었는지 확인합니다.
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        assertThat(findChatRoom.getJoinedMemberIds()).containsExactly(user1.getId(), user2.getId(), user3.getId());

        // 새로운 멤버가 채팅방에 참여한 것으로 추가되었는지 확인합니다.
        ChatMember findChatMember2 = chatMemberRepository.findById(user2.getId());
        assertThat(findChatMember2.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember2.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());

        ChatMember findChatMember3 = chatMemberRepository.findById(user3.getId());
        assertThat(findChatMember3.getJoinedChatRooms().size()).isEqualTo(1);
        assertThat(findChatMember3.getJoinedChatRooms().get(0).getChatRoomId()).isEqualTo(chatRoom.getId());
    }
}