package com.example.backend.controller.user.profile;

import com.example.backend.controller.dto.response.MyProfileViewResponseDto;
import com.example.backend.service.profile.ProfileViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class ProfileViewController {
    private final ProfileViewService profileViewService;

    @GetMapping
    public ResponseEntity<MyProfileViewResponseDto> getProfile() {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(profileViewService.gerProfile(memberId));
    }
}
