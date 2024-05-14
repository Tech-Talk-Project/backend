package com.example.backend.service.member;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.entity.follow.Following;
import com.example.backend.entity.member.EAuthority;
import com.example.backend.entity.member.Member;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.repository.follow.FollowingRepository;
import com.example.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCreateService {
    private final MemberRepository memberRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final FollowingRepository followingRepository;

    public Member createUser(UserProfileDto userProfileDto, OAuth2Provider oAuth2Provider) {
        String email = userProfileDto.getEmail();
        String name = userProfileDto.getName();
        String imageUrl = userProfileDto.getImageUrl();
        Member member = Member.builder()
                .email(email)
                .name(name)
                .imageUrl(imageUrl)
                .oAuth2Provider(oAuth2Provider)
                .eAuthorities(List.of(EAuthority.ROLE_USER))
                .build();
        memberRepository.save(member);

        chatMemberRepository.save(new ChatMember(member.getId()));
        followingRepository.save(new Following(member.getId()));
        return member;
    }
}
