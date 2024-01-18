package com.example.backend.chat.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatMessage;
import com.example.backend.chat.domain.ChatRoom;
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
class ChatMessageServiceTest {
    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomManageService chatRoomManageService;

    @Autowired
    private ChatMemberService chatMemberService;

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


    @DisplayName("send 메소드 테스트")
    @Test
    void send() {
        // given
        Member user = memberCreateService.createUser(
                new UserProfileDto("test1", "test1@com", "test1"), OAuth2Provider.GITHUB
        );
        ChatRoom chatRoom = chatRoomManageService.createChatRoom("test", List.of(user.getId()));


        // when
        chatMessageService.send(chatRoom.getId(), user.getId(), "hello");
        chatMessageService.send(chatRoom.getId(), user.getId(), "world");

        // then
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId());
        assertThat(findChatRoom.getLastMessages().size()).isEqualTo(3);
    }
}