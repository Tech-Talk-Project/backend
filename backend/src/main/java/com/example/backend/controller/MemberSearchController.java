package com.example.backend.controller;

import com.example.backend.controller.dto.response.MemberSearchResponseDto;
import com.example.backend.service.member.MemberSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberSearchController {
    private final MemberSearchService memberSearchService;

    @GetMapping("/members/search")
    public ResponseEntity<MemberSearchResponseDto> searchMembers(
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(memberSearchService.findByEmailStartsWithLimit(email, limit));
    }
}
