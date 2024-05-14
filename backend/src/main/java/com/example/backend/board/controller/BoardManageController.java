package com.example.backend.board.controller;

import com.example.backend.board.dto.request.*;
import com.example.backend.board.dto.response.CheckDislikeResponseDto;
import com.example.backend.board.dto.response.CheckLikeResponseDto;
import com.example.backend.board.dto.response.BoardCreateResponseDto;
import com.example.backend.board.service.*;
import com.example.backend.board.service.boardManage.*;
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
    private final BoardManageServiceFactory boardManageServiceFactory;

    @PostMapping("/create")
    public ResponseEntity<BoardCreateResponseDto> createProject(@RequestBody BoardCreateRequestDto dto) {
        log.info("createBoard called");
        BoardManageService boardManageService = boardManageServiceFactory.get(dto.getCategory());

        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long boardId = boardManageService.create(memberId, dto);
        return ResponseEntity.ok(new BoardCreateResponseDto(dto.getCategory(), boardId));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProject(
            @RequestBody BoardUpdateRequestDto dto) {
        log.info("updateBoard called");
        BoardManageService boardManageService = boardManageServiceFactory.get(dto.getCategory());

        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boardManageService.update(memberId, dto);
        return ResponseEntity.ok("수정 완료");
    }

    @PostMapping("/toggle-recruitment")
    public ResponseEntity<String> toggleRecruitment(
            @RequestBody BoardUpdateRequestDto dto) {
        log.info("toggleRecruitment called");
        BoardManageService boardManageService = boardManageServiceFactory.getRecruitBoard(dto.getCategory());

        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boardManageService.toggleRecruitment(memberId, dto);
        return ResponseEntity.ok("모집 상태 변경 완료");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteProject(
            @RequestBody BoardDeleteRequestDto dto) {
        BoardManageService boardManageService = boardManageServiceFactory.get(dto.getCategory());

        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boardManageService.delete(memberId, dto);
        return ResponseEntity.ok("게시글 삭제 완료");
    }

    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(
            @RequestBody CommentAddRequestDto dto) {
        BoardManageService boardManageService = boardManageServiceFactory.get(dto.getCategory());

        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boardManageService.addComment(memberId, dto);
        return ResponseEntity.ok("댓글 추가 완료");
    }

    @PostMapping("/toggle-like")
    public ResponseEntity<String> toggleLike(
            @RequestBody LikeRequestDto dto) {
        BoardManageService boardManageService = boardManageServiceFactory.get(dto.getCategory());
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean result = boardManageService.toggleLike(memberId, dto);
        return result ? ResponseEntity.ok("좋아요 완료") : ResponseEntity.ok("좋아요 취소");
    }

    @GetMapping("/check-like")
    public ResponseEntity<CheckLikeResponseDto> checkLike(
            @RequestParam Long boardId,
            @RequestParam BoardCategory category) {
        BoardManageService boardManageService = boardManageServiceFactory.get(category);
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean result = boardManageService.checkLike(memberId, boardId);
        return ResponseEntity.ok(new CheckLikeResponseDto(result));
    }

    @PostMapping("/toggle-dislike")
    public ResponseEntity<String> toggleDislike(
            @RequestBody LikeRequestDto dto) {
        BoardManageService boardManageService = boardManageServiceFactory.get(dto.getCategory());
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = boardManageService.toggleDislike(memberId, dto);
        return result ? ResponseEntity.ok("싫어요 완료") : ResponseEntity.ok("싫어요 취소");
    }

    @GetMapping("/check-dislike")
    public ResponseEntity<CheckDislikeResponseDto> checkDislike(
            @RequestParam Long boardId,
            @RequestParam BoardCategory category) {
        BoardManageService boardManageService = boardManageServiceFactory.get(category);
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean result = boardManageService.checkDislike(memberId, boardId);
        return ResponseEntity.ok(new CheckDislikeResponseDto(result));
    }
}
