package com.example.backend.controller.user.follow;

import com.example.backend.controller.dto.request.FollowUpdateDto;
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

    @PostMapping("/add")
    public ResponseEntity<String> addFollowing(@RequestBody FollowUpdateDto followUpdateDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        followService.addFollowing(memberId, followUpdateDto.getMemberId());
        return ResponseEntity.ok("팔로워가 추가되었습니다.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFollowing(@RequestBody FollowUpdateDto followUpdateDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        followService.removeFollowing(memberId, followUpdateDto.getMemberId());
        return ResponseEntity.ok("팔로워가 제거되었습니다.");
    }

    @GetMapping("/followings")
    public ResponseEntity<ProfilePaginationResponseDto> getFollowingsAfterCursor(
            @RequestParam String cursor,
            @RequestParam(defaultValue = "15") int limit) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfilePaginationResponseDto profilePaginationResponseDto = followService.getFollowingsAfterCursor(cursor, limit, memberId);
        return ResponseEntity.ok(profilePaginationResponseDto);
    }

}
