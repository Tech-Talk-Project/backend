package com.example.backend.service.follow;

import com.example.backend.controller.dto.response.ProfilePaginationByUpdatedResponseDto;
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

//    public ProfilePaginationByUpdatedResponseDto getFollowingsAfterCursor(Long memberId, Long cursor, int limit) {
//        Following following = followingRepository.findById(memberId);
//        List<Member> members = new ArrayList<>();
//
//
//
//    }

    public boolean isFollowing(Long memberId, Long followingId) {
        return followingRepository.existsFollowingId(memberId, followingId);
    }
}
