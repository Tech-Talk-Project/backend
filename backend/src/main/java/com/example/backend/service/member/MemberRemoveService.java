package com.example.backend.service.member;

import com.example.backend.board.repository.*;
import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.service.ChatRoomManageService;
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
    private final FollowingRepository followingRepository;
    private final ChatRoomManageService chatRoomManageService;

    private final FreeBoardRepository freeBoardRepository;
    private final ProjectBoardRepository projectBoardRepository;
    private final PromotionBoardRepository promotionBoardRepository;
    private final QuestionBoardRepository questionBoardRepository;
    private final StudyBoardRepository studyBoardRepository;
    private final CommentRepository commentRepository;

    public void removeMember(Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            chatRoomManageService.leaveChatRoom(memberId, joinedChatRoom.getChatRoomId());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        memberAuthorityRepository.deleteAuthoritiesByMemberId(memberId);
        memberRepository.deleteById(memberId);
        chatMemberRepository.removeById(memberId);
        followingRepository.deleteById(memberId);
        setRemovedMember(memberId);
    }

    private void setRemovedMember(Long memberId) {
        freeBoardRepository.setNullMember(memberId);
        projectBoardRepository.setNullMember(memberId);
        promotionBoardRepository.setNullMember(memberId);
        questionBoardRepository.setNullMember(memberId);
        studyBoardRepository.setNullMember(memberId);
        commentRepository.setNullMember(memberId);
    }
}
