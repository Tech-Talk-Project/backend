package com.example.backend.service.follow;

import com.example.backend.controller.dto.response.ProfilePaginationResponseDto;
import com.example.backend.entity.follow.Following;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.follow.FollowingRepository;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowingRepository followingRepository;
    private final MemberProfileRepository memberProfileRepository;

    public void addFollowing(Long memberId, Long followingId) {
        Following.Person followingPerson = new Following.Person(followingId);
        followingRepository.addFollowing(memberId, followingPerson);
    }

    public void removeFollowing(Long memberId, Long followingId) {
        followingRepository.removeFollowing(memberId, followingId);
    }

    public ProfilePaginationResponseDto getFollowingsAfterCursor(Long memberId, Date cursor, int limit) {
        List<Following.Person> followingsByCursor = followingRepository.getFollowingsByCursor(memberId, cursor, limit);
        List<Member> members = new ArrayList<>();
        for (Following.Person followingPerson : followingsByCursor) {
            Member member = memberProfileRepository.findByIdWithProfileInfo(followingPerson.getId());
            members.add(member);
        }

        String nextCursor = members.isEmpty() ? "" : followingsByCursor.get(followingsByCursor.size() - 1).getFollowingTime().toString();
        return new ProfilePaginationResponseDto(members, nextCursor);
    }

    public boolean isFollowing(Long memberId, Long followingId) {
        return followingRepository.existsByMemberIdInFollowingPerson(memberId, followingId);
    }
}
