package com.example.backend.service.profile;

import com.example.backend.controller.dto.request.UpdateSkillsRequestDto;
import com.example.backend.controller.dto.response.ProfilePaginationByUpdatedResponseDto;
import com.example.backend.entity.member.Member;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.service.member.MemberCreateService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ProfilePaginationServiceTest {
    @Autowired
    ProfilePaginationService profilePaginationService;

    @Autowired
    MemberCreateService memberCreateService;

    @Autowired
    ProfileUpdateService profileUpdateService;

    @Autowired
    EntityManager em;


    /**
     * 스킬을 가지고 있지 않더라도 전체 조회 (빈 리스트로 조회) 하면 조회가 되어야 합니다.
     * 스킬을 가지고 있지 않을 때 특정 skill 을 조회하면 조회가 되지 않아야 합니다.
     */
    @DisplayName("스킬이 없는 경우 테스트")
    @Test
    public void paginationTestByOneWithNullSkill() {

        UserProfileDto userProfileDto = new UserProfileDto("student", "test", "test");
        Member user = memberCreateService.createUser(userProfileDto, OAuth2Provider.GITHUB);

        em.flush();
        em.clear();


        ProfilePaginationByUpdatedResponseDto searchAll =
                profilePaginationService.getProfilesAfterCursorBySkills("2025-08-01T00:00:00", 10, List.of());
        ProfilePaginationByUpdatedResponseDto searchOnlyJavaContained =
                profilePaginationService.getProfilesAfterCursorBySkills("2025-08-01T00:00:00", 10, List.of("JAVA"));

        assertThat(searchAll.getCount()).isEqualTo(1);
        assertThat(searchOnlyJavaContained.getCount()).isEqualTo(0);
    }

    @DisplayName("유저가 스킬을 보유한 경우 조건에 해당되는 것만 조회되는지 테스트 - 스킬 1개")
    @Test
    public void paginationTestByOne() {
        String[] skills = new String[]{"JAVA", "PYTHON", "JavaScript", "Ruby"};
        for (String skill : skills) {
            UserProfileDto userProfileDto = new UserProfileDto("student", skill + "@com", "test");
            Member user = memberCreateService.createUser(userProfileDto, OAuth2Provider.GITHUB);

            UpdateSkillsRequestDto updateSkillsRequestDto = new UpdateSkillsRequestDto();
            updateSkillsRequestDto.setSkills(List.of(new String[]{skill}));
            profileUpdateService.updateSkills(user.getId(), updateSkillsRequestDto);
        }

        em.flush();
        em.clear();

        ProfilePaginationByUpdatedResponseDto profilesAfterCursor
                = profilePaginationService.getProfilesAfterCursorBySkills("2025-08-01T00:00:00", 10, List.of("JAVA"));
        assertThat(profilesAfterCursor.getCount()).isEqualTo(1);
    }

    @DisplayName("유저가 스킬을 보유한 경우 조건에 해당되는 것만 조회되는지 테스트 - 스킬 2개 이상")
    @Test
    public void paginationTestByTwo() {
        String[] skills = new String[]{"JAVA", "PYTHON", "JavaScript", "Ruby"};
        for (int i=1;i<=3;i++) {
            UserProfileDto userProfileDto = new UserProfileDto("student" + i, "test" + i, "test" + i);
            Member user = memberCreateService.createUser(userProfileDto, OAuth2Provider.GITHUB);

            UpdateSkillsRequestDto updateSkillsRequestDto = new UpdateSkillsRequestDto();
            updateSkillsRequestDto.setSkills(List.of("Java", skills[i]));
            profileUpdateService.updateSkills(user.getId(), updateSkillsRequestDto);
        }
        em.flush();
        em.clear();

        ProfilePaginationByUpdatedResponseDto profilesAfterCursor =
                profilePaginationService.getProfilesAfterCursorBySkills("2025-08-01T00:00:00", 10, List.of("Java", "Python"));
        assertThat(profilesAfterCursor.getCount()).isEqualTo(1);
    }
}