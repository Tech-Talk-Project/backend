package com.example.backend.service.profile;

import com.example.backend.controller.dto.request.UpdateDescRequestDto;
import com.example.backend.controller.dto.request.UpdateInfoRequestDto;
import com.example.backend.controller.dto.request.UpdateIntroductionRequestDto;
import com.example.backend.controller.dto.request.UpdateLinksRequestDto;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.Link;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.repository.profile.MemberProfileRepository;
import com.example.backend.service.member.MemberCreateService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfileUpdateServiceTest {
    @Autowired
    private MemberCreateService memberCreateService;

    @Autowired
    private ProfileUpdateService profileUpdateService;

    @Autowired
    MemberProfileRepository memberProfileRepository;

    @Autowired
    EntityManager em;

    private Long memberId;

    @BeforeEach
    void setUp() {
        UserProfileDto userProfileDto = new UserProfileDto("test", "test@com", "test.com");
        Member user = memberCreateService.createUser(userProfileDto, OAuth2Provider.GITHUB);
        memberId = user.getId();
    }

    void clear() {
        em.flush();
        em.clear();
    }

    @Test
    void updateProfileInfo() {
        // given
        // setUp()

        Member member = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        Assertions.assertThat(member.getName()).isEqualTo("test");
        Assertions.assertThat(member.getProfile().getJob()).isEqualTo(null);

        // when
        UpdateInfoRequestDto updateInfoRequestDto = new UpdateInfoRequestDto("new name", "new job");
        profileUpdateService.updateInfo(memberId, updateInfoRequestDto);
        clear();

        // then
        Member updatedMember = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        Assertions.assertThat(updatedMember.getName()).isEqualTo("new name");
        Assertions.assertThat(updatedMember.getProfile().getJob()).isEqualTo("new job");
    }

    @Test
    void updateProfileIntroduction() {
        // given
        // setUp()

        Member member = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        Assertions.assertThat(member.getProfile().getIntroduction()).isEqualTo(null);

        // when
        profileUpdateService.updateIntroduction(memberId, new UpdateIntroductionRequestDto("new introduction"));
        clear();

        // then
        Member updatedMember = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        Assertions.assertThat(updatedMember.getProfile().getIntroduction()).isEqualTo("new introduction");
    }

    @Test
    void updateProfileDescription() {
        // given
        // setUp()

        Member member = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        Assertions.assertThat(member.getProfile().getDetailedDescription()).isEqualTo(null);

        // when
        profileUpdateService.updateDescription(memberId, new UpdateDescRequestDto("new description"));
        clear();

        // then
        Member updatedMember = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        Assertions.assertThat(updatedMember.getProfile().getDetailedDescription()).isEqualTo("new description");
    }

    @Test
    void updateProfileLinks() {
        // given
        // setUp()

        Member member = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        Assertions.assertThat(member.getProfile().getLinks()).isEmpty();

        // when
        profileUpdateService.updateLinks(memberId, new UpdateLinksRequestDto(List.of("new link1", "new link2")));
        clear();

        // then
        Member updatedMember = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );

        List<String> links = updatedMember.getProfile().getLinks().stream().map(Link::getUrl).toList();
        Assertions.assertThat(links).containsExactly("new link1", "new link2");
    }
}