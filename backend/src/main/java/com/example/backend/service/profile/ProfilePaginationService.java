package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.ProfilePaginationResponseDto;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Profile;
import com.example.backend.repository.profile.ProfilePaginationRepository;
import com.example.backend.repository.profile.SkillRepository;
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
    private final SkillRepository skillRepository;

    public ProfilePaginationResponseDto getProfilesAfterCursor(
            String cursor, int limit, List<String> strSkills) {

        List<ESkill> eSkills = strSkills.stream().map(ESkill::from).toList();

        LocalDateTime cursorDateTime;
        if (cursor == null) {
            cursorDateTime = LocalDateTime.now();
        } else {
            cursorDateTime = LocalDateTime.parse(cursor);
        }

        List<Profile> profiles;
        if (eSkills.isEmpty()) {
            profiles = profilePaginationRepository.pagingAfterCursor(cursorDateTime, limit);
        } else {
            profiles = profilePaginationRepository.pagingBySkillsAfterCursor(cursorDateTime, limit, eSkills);
        }
        return new ProfilePaginationResponseDto(profiles);
    }


}
