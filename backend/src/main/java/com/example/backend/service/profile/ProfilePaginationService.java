package com.example.backend.service.profile;

import com.example.backend.controller.dto.ProfileDto;
import com.example.backend.controller.dto.response.AuthProfilePaginationDto;
import com.example.backend.controller.dto.response.ProfileCursorPaginationResponseDto;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.repository.follow.FollowingRepository;
import com.example.backend.repository.profile.ProfilePaginationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfilePaginationService {
    private final ProfilePaginationRepository profilePaginationRepository;
    private final FollowingRepository followingRepository;

    /**
     * cursor 이후의 limit 개수 만큼의 프로필을 조회합니다. <br>
     * strSkills 가 비어있으면 전체 조회합니다.
     * @param cursor
     * @param limit
     * @param strSkills
     * @return
     */
    public ProfileCursorPaginationResponseDto getProfilesAfterCursorBySkills(
            String cursor, int limit, List<String> strSkills) {
        LocalDateTime cursorDateTime = getCursorDateTime(cursor);

        List<ESkill> eSkills = strSkills.stream().map(ESkill::from).toList();
        List<Member> members = profilePaginationRepository.pagingBySkillsAfterCursor(cursorDateTime, limit, eSkills);

        return new ProfileCursorPaginationResponseDto(members);
    }

    public AuthProfilePaginationDto getProfileAfterCursorBySkillsWhenLogin(
            String cursor, int limit, List<String> strSkills, Long memberId) {
        LocalDateTime cursorDateTime = getCursorDateTime(cursor);

        List<ESkill> eSkills = strSkills.stream().map(ESkill::from).toList();
        List<Member> members = profilePaginationRepository.pagingBySkillsAfterCursor(cursorDateTime, limit, eSkills);

        List<Long> followingIds = followingRepository.findById(memberId).getFollowingIds();
        Collections.sort(followingIds);

        List<ProfileDto> data = new ArrayList<>();
        for (Member member : members) {
            ProfileDto profile = ProfileDto.builder()
                    .memberId(member.getId())
                    .name(member.getName())
                    .job(member.getProfile().getJob())
                    .introduction(member.getProfile().getIntroduction())
                    .imageUrl(member.getImageUrl())
                    .skills(member.getProfile().getSkills())
                    .isFollowing(Collections.binarySearch(followingIds, member.getId()) >= 0)
                    .build();
            data.add(profile);
        }
        String nextCursor = members.isEmpty() ? null : members.get(members.size() - 1).getUpdatedAt().toString();
        int count = members.size();

        return new AuthProfilePaginationDto(data, nextCursor, count);
    }

    public ProfileCursorPaginationResponseDto getProfilesAfterCursor(String cursor, int limit) {
        LocalDateTime cursorDateTime = getCursorDateTime(cursor);
        List<Member> members = profilePaginationRepository.pagingAfterCursor(cursorDateTime, limit);
        return new ProfileCursorPaginationResponseDto(members);
    }

    private LocalDateTime getCursorDateTime(String cursor) {
        if (cursor == null) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(cursor);
    }
}
