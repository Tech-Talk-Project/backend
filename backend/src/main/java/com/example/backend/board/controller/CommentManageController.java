package com.example.backend.board.controller;

import com.example.backend.board.dto.request.CommentUpdateRequestDto;
import com.example.backend.board.service.CommentManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/comment")
@Slf4j
public class CommentManageController {

    private final CommentManageService commentManageService;

    @PostMapping("/update")
    public ResponseEntity<String> updateComment(
            @RequestBody CommentUpdateRequestDto dto
    ) {
        log.info("updateComment api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentManageService.updateComment(memberId, dto);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteComment(
            @RequestBody CommentUpdateRequestDto dto
    ) {
        log.info("deleteComment api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentManageService.deleteComment(memberId, dto);
        return ResponseEntity.ok("댓글 삭제 완료");
    }
}
