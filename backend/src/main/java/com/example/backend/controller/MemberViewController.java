package com.example.backend.controller;

import com.example.backend.controller.dto.request.MembersViewRequestDto;
import com.example.backend.controller.dto.response.ProfilePaginationByUpdatedResponseDto;
import com.example.backend.controller.dto.response.ProfileViewResponseDto;
import com.example.backend.service.profile.ProfilePaginationService;
import com.example.backend.service.profile.ProfileViewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProfileViewResponseDto> getProfile(
            @RequestParam Long memberId,
            @RequestParam Long selectedMemberId
    ) {
        ProfileViewResponseDto profile = profileViewService.getProfile(memberId, selectedMemberId);
        return ResponseEntity.ok(profile);
    }

}
