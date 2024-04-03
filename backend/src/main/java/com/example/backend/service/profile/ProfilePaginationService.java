package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.ProfilePaginationByUpdatedResponseDto;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.repository.profile.ProfilePaginationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfilePaginationService {
    private final ProfilePaginationRepository profilePaginationRepository;

    /**
     * cursor 이후의 limit 개수 만큼의 프로필을 조회합니다. <br>
     * strSkills 가 비어있으면 전체 조회합니다.
     * @param cursor
     * @param limit
     * @param strSkills
     * @return
     */
    public ProfilePaginationByUpdatedResponseDto getProfilesAfterCursorBySkills(
            String cursor, int limit, List<String> strSkills) {
        LocalDateTime cursorDateTime = getCursorDateTime(cursor);

        List<ESkill> eSkills = strSkills.stream().map(ESkill::from).toList();

        List<Member> members = profilePaginationRepository.pagingBySkillsAfterCursor(cursorDateTime, limit, eSkills);

        return new ProfilePaginationByUpdatedResponseDto(members);
    }

    public ProfilePaginationByUpdatedResponseDto getProfilesAfterCursor(String cursor, int limit) {
        LocalDateTime cursorDateTime = getCursorDateTime(cursor);
        List<Member> members = profilePaginationRepository.pagingAfterCursor(cursorDateTime, limit);
        return new ProfilePaginationByUpdatedResponseDto(members);
    }

    private LocalDateTime getCursorDateTime(String cursor) {
        if (cursor == null) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(cursor);
    }
}
