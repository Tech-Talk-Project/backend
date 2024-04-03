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

    public ProfilePaginationResponseDto getFollowingsWithSlice(Long memberId, int pageNumber, int pageSize) {
        Following following = followingRepository.findById(memberId);
        List<Long> followingIds = following.getFollowingIds();
        int fromIndex = (pageNumber-1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, followingIds.size());

        List<Member> members = new ArrayList<>();
        for (Long followingId : followingIds.subList(fromIndex, toIndex)) {
            members.add(memberProfileRepository.findByIdWithProfileWithSkills(followingId));
        }
        return new ProfilePaginationResponseDto(members);
    }

    public boolean isFollowing(Long memberId, Long followingId) {
        return followingRepository.existsFollowingId(memberId, followingId);
    }
}
