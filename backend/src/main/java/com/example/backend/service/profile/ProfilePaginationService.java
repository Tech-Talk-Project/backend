package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.ProfilePaginationResponseDto;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Profile;
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
    public ProfilePaginationResponseDto getProfilesAfterCursorBySkills(
            String cursor, int limit, List<String> strSkills) {
        LocalDateTime cursorDateTime = getCursorDateTime(cursor);

        List<ESkill> eSkills = strSkills.stream().map(ESkill::from).toList();

        List<Profile> profiles = profilePaginationRepository.pagingBySkillsAfterCursor(cursorDateTime, limit, eSkills);

        return new ProfilePaginationResponseDto(profiles);
    }

    public ProfilePaginationResponseDto getProfilesAfterCursor(String cursor, int limit) {
        LocalDateTime cursorDateTime = getCursorDateTime(cursor);
        List<Profile> profiles = profilePaginationRepository.pagingAfterCursor(cursorDateTime, limit);
        return new ProfilePaginationResponseDto(profiles);
    }

    private LocalDateTime getCursorDateTime(String cursor) {
        if (cursor == null) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(cursor);
    }

}
