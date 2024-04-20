package com.example.backend.board.controller;

import com.example.backend.board.dto.request.*;
import com.example.backend.board.dto.response.CheckLikeResponseDto;
import com.example.backend.board.dto.response.BoardCreateResponseDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.service.ProjectBoardManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/board")
@Slf4j
public class BoardManageController {
    private final ProjectBoardManageService projectBoardManageService;

    @PostMapping("/create")
    public ResponseEntity<BoardCreateResponseDto> createProject(@RequestBody BoardCreateRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : createBoard api called");
                Long projectBoardId = projectBoardManageService.create(memberId, dto);
                return ResponseEntity.ok(new BoardCreateResponseDto(BoardCategory.PROJECT, projectBoardId));
            case STUDY:
                log.info("STUDY : createBoard api called");
                break;
            case QUESTION:
                log.info("QUESTION : createBoard api called");
                break;
            case PROMOTION:
                log.info("PROMOTION : createBoard api called");
                break;
            case FREE:
                log.info("FREE : createBoard api called");
                break;
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body(new BoardCreateResponseDto(null, null));
        }
        return ResponseEntity.badRequest().body(new BoardCreateResponseDto(null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProject(
            @RequestBody BoardUpdateRequestDto dto) {
        log.info("updateProject api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.update(memberId, dto);
        return ResponseEntity.ok("프로젝트 수정 완료");
    }

    @PostMapping("/toggle-recruitment")
    public ResponseEntity<String> toggleRecruitment(
            @RequestBody BoardUpdateRequestDto dto) {
        log.info("toggleRecruitment api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.toggleRecruitment(memberId, dto);
        return ResponseEntity.ok("모집 상태 변경 완료");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteProject(
            @RequestBody BoardDeleteRequestDto dto) {
        log.info("deleteProject api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.delete(memberId, dto);
        return ResponseEntity.ok("프로젝트 삭제 완료");
    }

    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(
            @RequestBody CommentAddRequestDto dto) {
        log.info("addComment api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.addComment(memberId, dto);
        return ResponseEntity.ok("댓글이 추가되었습니다.");
    }

    @PostMapping("/toggle-like")
    public ResponseEntity<String> toggleLike(
            @RequestBody LikeRequestDto dto) {
        log.info("toggleLike api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = projectBoardManageService.toggleLike(memberId, dto);
        if (result) {
            return ResponseEntity.ok("좋아요가 추가되었습니다.");
        } else {
            return ResponseEntity.ok("좋아요가 삭제되었습니다.");
        }
    }

    @GetMapping("/check-like")
    public ResponseEntity<CheckLikeResponseDto> checkLike(
            @RequestParam Long boardId) {
        log.info("checkLike api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = projectBoardManageService.checkLike(memberId, boardId);
        return ResponseEntity.ok(new CheckLikeResponseDto(result));
    }
}
