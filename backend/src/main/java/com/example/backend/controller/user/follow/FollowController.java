package com.example.backend.controller.user.follow;

import com.example.backend.controller.dto.response.ProfilePaginationResponseDto;
import com.example.backend.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    

    @GetMapping("/followings")
    public ResponseEntity<ProfilePaginationResponseDto> getFollowingsAfterCursor(
            @RequestParam String cursor,
            @RequestParam(defaultValue = "15") int limit) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfilePaginationResponseDto profilePaginationResponseDto = followService.getFollowingsAfterCursor(cursor, limit, memberId);
        return ResponseEntity.ok(profilePaginationResponseDto);
    }

}
