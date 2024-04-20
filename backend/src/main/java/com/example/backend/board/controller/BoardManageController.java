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
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : updateBoard api called");
                projectBoardManageService.update(memberId, dto);
                return ResponseEntity.ok("프로젝트 수정 완료");
            case STUDY:
                log.info("STUDY : updateBoard api called");
                break;
            case QUESTION:
                log.info("QUESTION : updateBoard api called");
                break;
            case PROMOTION:
                log.info("PROMOTION : updateBoard api called");
                break;
            case FREE:
                log.info("FREE : updateBoard api called");
                break;
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body("Invalid category");
        }
        return ResponseEntity.badRequest().body("Invalid category");
    }

    @PostMapping("/toggle-recruitment")
    public ResponseEntity<String> toggleRecruitment(
            @RequestBody BoardUpdateRequestDto dto) {
        log.info("toggleRecruitment api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : toggleRecruitment api called");
                projectBoardManageService.toggleRecruitment(memberId, dto);
                return ResponseEntity.ok("모집 상태가 변경되었습니다.");
            case STUDY:
                log.info("STUDY : toggleRecruitment api called");
                break;
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body("Invalid category");
        }
        return ResponseEntity.badRequest().body("Invalid category");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteProject(
            @RequestBody BoardDeleteRequestDto dto) {
        log.info("deleteProject api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : deleteBoard api called");
                projectBoardManageService.delete(memberId, dto);
                return ResponseEntity.ok("프로젝트 삭제 완료");
            case STUDY:
                log.info("STUDY : deleteBoard api called");
                break;
            case QUESTION:
                log.info("QUESTION : deleteBoard api called");
                break;
            case PROMOTION:
                log.info("PROMOTION : deleteBoard api called");
                break;
            case FREE:
                log.info("FREE : deleteBoard api called");
                break;
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body("Invalid category");
        }
        return ResponseEntity.badRequest().body("Invalid category");
    }

    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(
            @RequestBody CommentAddRequestDto dto) {
        log.info("addComment api called");
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : addComment api called");
                projectBoardManageService.addComment(memberId, dto);
                return ResponseEntity.ok("댓글 추가 완료");
            case STUDY:
                log.info("STUDY : addComment api called");
                break;
            case QUESTION:
                log.info("QUESTION : addComment api called");
                break;
            case PROMOTION:
                log.info("PROMOTION : addComment api called");
                break;
            case FREE:
                log.info("FREE : addComment api called");
                break;
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body("Invalid category");
        }
        return ResponseEntity.badRequest().body("Invalid category");
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
