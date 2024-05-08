package com.example.backend.controller.user.profile;

import com.example.backend.controller.dto.request.*;
import com.example.backend.service.profile.ProfileUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile/update")
@RequiredArgsConstructor
@Slf4j
public class ProfileUpdateController {
    private final ProfileUpdateService profileUpdateService;

    @PostMapping("/info")
    public ResponseEntity<String> updateInfo(@Valid @RequestBody UpdateInfoRequestDto updateInfoRequestDto) {
        log.info("updateInfo api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateInfo(memberId, updateInfoRequestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/introduction")
    public ResponseEntity<String> updateIntroduction(@Valid @RequestBody UpdateIntroductionRequestDto updateIntroductionRequestDto) {
        log.info("updateIntroduction api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateIntroduction(memberId, updateIntroductionRequestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/description")
    public ResponseEntity<String> updateDescription(@Valid @RequestBody UpdateDescRequestDto updateDescRequestDto) {
        log.info("updateDescription api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateDescription(memberId, updateDescRequestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/links")
    public ResponseEntity<String> updateLinks(@Valid @RequestBody UpdateLinksRequestDto updateLinksRequestDto) {
        log.info("updateLinks api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateLinks(memberId, updateLinksRequestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/skills")
    public ResponseEntity<String> updateSkills(@Valid @RequestBody UpdateSkillsRequestDto updateSkillsRequestDto) {
        log.info("updateSkills api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateSkills(memberId, updateSkillsRequestDto);
        return ResponseEntity.ok("success");
    }
}
