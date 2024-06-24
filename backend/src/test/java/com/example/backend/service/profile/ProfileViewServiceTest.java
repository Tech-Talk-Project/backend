package com.example.backend.service.profile;

import com.example.backend.controller.dto.request.*;
import com.example.backend.controller.dto.response.ProfileResponseDto;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.service.member.MemberCreateService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * ProfileUpdateServiceTest 에 종속되는 테스트 입니다.
 * ProfileUpdateServiceTest 가 전부 통과된다는 가정하에 테스트를 진행합니다.
 */
@SpringBootTest
@Transactional
class ProfileViewServiceTest {

    @Autowired
    private ProfileViewService profileViewService;

    @Autowired
    private MemberCreateService memberCreateService;

    @Autowired
    private ProfileUpdateService profileUpdateService;

    @Autowired
    private EntityManager em;

    void clear() {
        em.flush();
        em.clear();
    }

    @Test
    void getProfile() {
        // given
        UserProfileDto userProfileDto = new UserProfileDto("test", "test@com", "test.com");
        Member user = memberCreateService.createUser(userProfileDto, OAuth2Provider.GITHUB);

        UpdateInfoRequestDto updateInfoRequestDto = new UpdateInfoRequestDto("new name", "new job");
        profileUpdateService.updateInfo(user.getId(), updateInfoRequestDto);

        UpdateDescRequestDto updateDescRequestDto = new UpdateDescRequestDto("new description");
        profileUpdateService.updateDescription(user.getId(), updateDescRequestDto);

        UpdateIntroductionRequestDto updateIntroductionRequestDto = new UpdateIntroductionRequestDto("new introduction");
        profileUpdateService.updateIntroduction(user.getId(), updateIntroductionRequestDto);

        UpdateSkillsRequestDto updateSkillsRequestDto = new UpdateSkillsRequestDto(List.of("python", "java", "javascript"));
        profileUpdateService.updateSkills(user.getId(), updateSkillsRequestDto);

        UpdateLinksRequestDto updateLinksRequestDto = new UpdateLinksRequestDto(List.of("new link1", "new link2"));
        profileUpdateService.updateLinks(user.getId(), updateLinksRequestDto);

        clear();

        // when
        ProfileResponseDto profile = profileViewService.getProfile(user.getId());

        // then
        assertEquals(profile.getInfo().getName(), "new name");
        assertEquals(profile.getInfo().getJob(), "new job");
        assertEquals(profile.getDetailedDescription(), "new description");
        assertEquals(profile.getIntroduction(), "new introduction");
        assertEquals(profile.getSkills(), List.of(ESkill.PYTHON.getName(), ESkill.JAVA.getName(), ESkill.JAVASCRIPT.getName()));
        assertEquals(profile.getLinks(), List.of("new link1", "new link2"));
    }
}