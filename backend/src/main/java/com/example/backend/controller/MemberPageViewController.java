package com.example.backend.controller;

import com.example.backend.controller.dto.request.MembersViewRequestDto;
import com.example.backend.controller.dto.response.ProfilePaginationResponseDto;
import com.example.backend.service.profile.ProfilePaginationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberPageViewController {
    private final ProfilePaginationService profilePaginationService;

    @GetMapping("/members")
    public ResponseEntity<ProfilePaginationResponseDto> getProfilesAfterCursor(
            @Valid @RequestBody MembersViewRequestDto membersViewRequestDto
            ) {
        String cursor = membersViewRequestDto.getCursor();
        int limit = membersViewRequestDto.getLimit();
        List<String> skills = membersViewRequestDto.getSkills();
        ProfilePaginationResponseDto profilesAfterCursor =
                profilePaginationService.getProfilesAfterCursor(cursor, limit, skills);
        return ResponseEntity.ok(profilesAfterCursor);
    }
}
