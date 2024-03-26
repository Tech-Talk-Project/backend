package com.example.backend.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.service.ChatMessageService;
import com.example.backend.chat.service.MemberId;
import com.example.backend.repository.member.AuthorityRepository;
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
    private final ChatMessageService chatMessageService;
    private final MemberAuthorityRepository memberAuthorityRepository;

    public void removeMember(Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        String memberName = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        ).getName();
        memberAuthorityRepository.deleteAuthoritiesByMemberId(memberId);
        memberRepository.deleteById(memberId);
        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            chatMessageService.send(joinedChatRoom.getChatRoomId(), MemberId.ADMIN.getValue(), memberName + "님이 나갔습니다.");
        }
    }
}
