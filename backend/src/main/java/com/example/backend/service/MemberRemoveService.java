package com.example.backend.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.service.ChatMemberService;
import com.example.backend.repository.follow.FollowingRepository;
import com.example.backend.repository.member.MemberAuthorityRepository;
import com.example.backend.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberRemoveService {
    private final MemberRepository memberRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final ChatMemberService chatMemberService;
    private final FollowingRepository followingRepository;

    public void removeMember(Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            chatMemberService.exitChatRoom(joinedChatRoom.getChatRoomId(), memberId);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        memberAuthorityRepository.deleteAuthoritiesByMemberId(memberId);
        memberRepository.deleteById(memberId);
        chatMemberRepository.deleteById(memberId);
        followingRepository.deleteById(memberId);
    }
}
