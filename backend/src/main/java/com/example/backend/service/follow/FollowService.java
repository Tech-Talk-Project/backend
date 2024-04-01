package com.example.backend.service.follow;

import com.example.backend.controller.dto.response.ProfilePaginationResponseDto;
import com.example.backend.entity.follow.Following;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.follow.FollowingRepository;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowingRepository followingRepository;
    private final MemberProfileRepository memberProfileRepository;

    public void addFollowing(Long memberId, Long followingId) {
        followingRepository.addFollowing(memberId, followingId);
    }

    public void removeFollowing(Long memberId, Long followingId) {
        followingRepository.removeFollowing(memberId, followingId);
    }

    public ProfilePaginationResponseDto getFollowingsAfterCursor(String cursor, int limit, Long memberId) {
        Set<Long> followingIds = followingRepository.findById(memberId).getFollowingIds();
        List<Member> members = new ArrayList<>();
        for (Long followingId : followingIds) {
            Member member = memberProfileRepository.findByIdWithProfileWithSkills(followingId);
            members.add(member);
        }

        return new ProfilePaginationResponseDto(members);
    }

    private boolean isFollowing(Long memberId, Long followingId) {
        Following following = followingRepository.findById(memberId);
        return following.getFollowingIds().contains(followingId);
    }
}