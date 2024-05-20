package com.example.backend.controller;

import com.example.backend.controller.dto.request.MembersViewRequestDto;
import com.example.backend.controller.dto.response.*;
import com.example.backend.service.profile.ProfilePaginationService;
import com.example.backend.service.profile.ProfileViewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberViewController {
    private final ProfilePaginationService profilePaginationService;
    private final ProfileViewService profileViewService;

    @PostMapping("/members")
    public ResponseEntity<ProfileCursorPaginationResponseDto> getProfilesAfterCursor(
            @Valid @RequestBody MembersViewRequestDto membersViewRequestDto
            ) {
        log.info("/members called");
        String cursor = membersViewRequestDto.getCursor();
        int limit = membersViewRequestDto.getLimit();
        List<String> skills = membersViewRequestDto.getSkills();
        ProfileCursorPaginationResponseDto profilesAfterCursor =
                profilePaginationService.getProfilesAfterCursorBySkills(cursor, limit, skills);
        return ResponseEntity.ok(profilesAfterCursor);
    }

    @PostMapping("/user/members")
    public ResponseEntity<AuthProfilePaginationDto> getProfilesAfterCursorWhenLogin(
            @Valid @RequestBody MembersViewRequestDto membersViewRequestDto
    ) {
        log.info("/user/members called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String cursor = membersViewRequestDto.getCursor();
        int limit = membersViewRequestDto.getLimit();
        List<String> skills = membersViewRequestDto.getSkills();
        AuthProfilePaginationDto result =
                profilePaginationService.getProfileAfterCursorBySkillsWhenLogin(cursor, limit, skills, memberId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/member")
    public ResponseEntity<ProfileResponseDto> getProfile(
            @RequestParam Long selectedMemberId
    ) {
        log.info("/member called");
        return ResponseEntity.ok(profileViewService.getProfile(selectedMemberId));
    }

    @GetMapping("/user/member")
    public ResponseEntity<AuthProfileResponseDto> getProfileWhenLogin(
            @RequestParam Long selectedMemberId
    ) {
        log.info("/user/member called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(profileViewService.getSelectedProfileWhenLogin(memberId, selectedMemberId));
    }
}
