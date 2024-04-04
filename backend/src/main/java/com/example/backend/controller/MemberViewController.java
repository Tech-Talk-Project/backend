package com.example.backend.controller;

import com.example.backend.controller.dto.request.MembersViewRequestDto;
import com.example.backend.controller.dto.response.AuthSelectedProfileResponseDto;
import com.example.backend.controller.dto.response.ProfilePaginationByUpdatedResponseDto;
import com.example.backend.controller.dto.response.SelectedProfileResponseDto;
import com.example.backend.service.profile.ProfilePaginationService;
import com.example.backend.service.profile.ProfileViewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberViewController {
    private final ProfilePaginationService profilePaginationService;
    private final ProfileViewService profileViewService;


    @PostMapping("/members")
    public ResponseEntity<ProfilePaginationByUpdatedResponseDto> getProfilesAfterCursor(
            @Valid @RequestBody MembersViewRequestDto membersViewRequestDto
            ) {
        String cursor = membersViewRequestDto.getCursor();
        int limit = membersViewRequestDto.getLimit();
        List<String> skills = membersViewRequestDto.getSkills();
        ProfilePaginationByUpdatedResponseDto profilesAfterCursor =
                profilePaginationService.getProfilesAfterCursorBySkills(cursor, limit, skills);
        return ResponseEntity.ok(profilesAfterCursor);
    }

    @GetMapping("/member")
    public ResponseEntity<SelectedProfileResponseDto> getProfile(
            @RequestParam Long selectedMemberId
    ) {
        return ResponseEntity.ok(profileViewService.getSelectedProfile(selectedMemberId));
    }

    @GetMapping("/user/member")
    public ResponseEntity<AuthSelectedProfileResponseDto> getProfileWhenLogin(
            @RequestParam Long selectedMemberId
    ) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(profileViewService.getSelectedProfileWhenLogin(memberId, selectedMemberId));
    }
}
