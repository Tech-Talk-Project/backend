package com.example.backend.board.controller;

import com.example.backend.board.dto.request.*;
import com.example.backend.board.dto.response.CheckLikeResponseDto;
import com.example.backend.board.dto.response.BoardCreateResponseDto;
import com.example.backend.board.service.*;
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
    private final StudyBoardManageService studyBoardManageService;
    private final QuestionBoardManageService questionBoardManageService;
    private final PromotionBoardManageService promotionBoardManageService;

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
                Long studyBoardId = studyBoardManageService.create(memberId, dto);
                return ResponseEntity.ok(new BoardCreateResponseDto(BoardCategory.STUDY, studyBoardId));
            case QUESTION:
                log.info("QUESTION : createBoard api called");
                Long questionBoardId = questionBoardManageService.create(memberId, dto);
                return ResponseEntity.ok(new BoardCreateResponseDto(BoardCategory.QUESTION, questionBoardId));
            case PROMOTION:
                log.info("PROMOTION : createBoard api called");
                Long promotionBoardId = promotionBoardManageService.create(memberId, dto);
                return ResponseEntity.ok(new BoardCreateResponseDto(BoardCategory.PROMOTION, promotionBoardId));
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
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : updateBoard api called");
                projectBoardManageService.update(memberId, dto);
                return ResponseEntity.ok("프로젝트 수정 완료");
            case STUDY:
                log.info("STUDY : updateBoard api called");
                studyBoardManageService.update(memberId, dto);
                return ResponseEntity.ok("스터디 수정 완료");
            case QUESTION:
                log.info("QUESTION : updateBoard api called");
                questionBoardManageService.update(memberId, dto);
                return ResponseEntity.ok("질문 수정 완료");
            case PROMOTION:
                log.info("PROMOTION : updateBoard api called");
                promotionBoardManageService.update(memberId, dto);
                return ResponseEntity.ok("홍보 수정 완료");
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
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : toggleRecruitment api called");
                projectBoardManageService.toggleRecruitment(memberId, dto);
                return ResponseEntity.ok("모집 상태가 변경되었습니다.");
            case STUDY:
                log.info("STUDY : toggleRecruitment api called");
                studyBoardManageService.toggleRecruitment(memberId, dto);
                return ResponseEntity.ok("모집 상태가 변경되었습니다.");
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body("Invalid category");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteProject(
            @RequestBody BoardDeleteRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : deleteBoard api called");
                projectBoardManageService.delete(memberId, dto);
                return ResponseEntity.ok("프로젝트 삭제 완료");
            case STUDY:
                log.info("STUDY : deleteBoard api called");
                studyBoardManageService.delete(memberId, dto);
                return ResponseEntity.ok("스터디 삭제 완료");
            case QUESTION:
                log.info("QUESTION : deleteBoard api called");
                questionBoardManageService.delete(memberId, dto);
                return ResponseEntity.ok("질문 삭제 완료");
            case PROMOTION:
                log.info("PROMOTION : deleteBoard api called");
                promotionBoardManageService.delete(memberId, dto);
                return ResponseEntity.ok("홍보 삭제 완료");
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
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : addComment api called");
                projectBoardManageService.addComment(memberId, dto);
                return ResponseEntity.ok("댓글 추가 완료");
            case STUDY:
                log.info("STUDY : addComment api called");
                studyBoardManageService.addComment(memberId, dto);
                return ResponseEntity.ok("댓글 추가 완료");
            case QUESTION:
                log.info("QUESTION : addComment api called");
                questionBoardManageService.addComment(memberId, dto);
                return ResponseEntity.ok("댓글 추가 완료");
            case PROMOTION:
                log.info("PROMOTION : addComment api called");
                promotionBoardManageService.addComment(memberId, dto);
                return ResponseEntity.ok("댓글 추가 완료");
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
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean result = false;
        switch (dto.getCategory()) {
            case PROJECT:
                log.info("PROJECT : toggleLike api called");
                result = projectBoardManageService.toggleLike(memberId, dto);
                break;
            case STUDY:
                log.info("STUDY : toggleLike api called");
                result = studyBoardManageService.toggleLike(memberId, dto);
                break;
            case QUESTION:
                log.info("QUESTION : toggleLike api called");
                result = questionBoardManageService.toggleLike(memberId, dto);
                break;
            case PROMOTION:
                log.info("PROMOTION : toggleLike api called");
                result = promotionBoardManageService.toggleLike(memberId, dto);
                break;
            case FREE:
                log.info("FREE : toggleLike api called");
                break;
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body("Invalid category");
        }
        return result ? ResponseEntity.ok("좋아요 완료") : ResponseEntity.ok("좋아요 취소");
    }

    @GetMapping("/check-like")
    public ResponseEntity<CheckLikeResponseDto> checkLike(
            @RequestParam Long boardId,
            @RequestParam BoardCategory category) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean result = false;
        switch (category) {
            case PROJECT:
                log.info("PROJECT : checkLike api called");
                result = projectBoardManageService.checkLike(memberId, boardId);
                break;
            case STUDY:
                log.info("STUDY : checkLike api called");
                result = studyBoardManageService.checkLike(memberId, boardId);
                break;
            case QUESTION:
                log.info("QUESTION : checkLike api called");
                result = questionBoardManageService.checkLike(memberId, boardId);
                break;
            case PROMOTION:
                log.info("PROMOTION : checkLike api called");
                result = promotionBoardManageService.checkLike(memberId, boardId);
                break;
            case FREE:
                log.info("FREE : checkLike api called");
                break;
            default:
                log.warn("Invalid category: {}", category);
                return ResponseEntity.badRequest().body(new CheckLikeResponseDto(false));
        }
        return ResponseEntity.ok(new CheckLikeResponseDto(result));
    }

    @PostMapping("/toggle-dislike")
    public ResponseEntity<String> toggleDislike(
            @RequestBody LikeRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = false;
        switch (dto.getCategory()) {
            case QUESTION:
                log.info("QUESTION : toggleDislike api called");
                result = questionBoardManageService.toggleDislike(memberId, dto);
                break;
            case PROMOTION:
                log.info("PROMOTION : toggleDislike api called");
                result = promotionBoardManageService.toggleDislike(memberId, dto);
                break;
            case FREE:
                log.info("FREE : toggleDislike api called");
                break;
            default:
                log.warn("Invalid category: {}", dto.getCategory());
                return ResponseEntity.badRequest().body("Invalid category");
        }
        return result ? ResponseEntity.ok("싫어요 완료") : ResponseEntity.ok("싫어요 취소");
    }

    @GetMapping("/check-dislike")
    public ResponseEntity<CheckLikeResponseDto> checkDislike(
            @RequestParam Long boardId,
            @RequestParam BoardCategory category) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = false;
        switch (category) {
            case QUESTION:
                log.info("QUESTION : checkDislike api called");
                result = questionBoardManageService.checkDislike(memberId, boardId);
                break;
            case PROMOTION:
                log.info("PROMOTION : checkDislike api called");
                result = promotionBoardManageService.checkDislike(memberId, boardId);
                break;
            case FREE:
                log.info("FREE : checkDislike api called");
                break;
            default:
                log.warn("Invalid category: {}", category);
                return ResponseEntity.badRequest().body(new CheckLikeResponseDto(false));
        }
        return ResponseEntity.ok(new CheckLikeResponseDto(result));
    }
}
