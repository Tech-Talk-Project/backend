package com.example.backend.service.follow;

import com.example.backend.controller.dto.response.ProfileOffsetPaginationResponseDto;
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

    public ProfileOffsetPaginationResponseDto getFollowingsWithSlice(Long memberId, int pageNumber, int pageSize) {
        Following following = followingRepository.findById(memberId);
        List<Long> followingIds = following.getFollowingIds();
        int totalCount = followingIds.size();
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        int fromIndex = (pageNumber-1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, followingIds.size());

        List<Member> members = new ArrayList<>();
        for (Long followingId : followingIds.subList(fromIndex, toIndex)) {
            memberProfileRepository.findByIdWithProfile(followingId)
                    .ifPresentOrElse(members::add
                            , () -> {followingRepository.removeFollowing(memberId, followingId);});
        }

        return new ProfileOffsetPaginationResponseDto(members, totalCount, totalPage);
    }

    public boolean isFollowing(Long memberId, Long followingId) {
        return followingRepository.existsFollowingId(memberId, followingId);
    }
}
