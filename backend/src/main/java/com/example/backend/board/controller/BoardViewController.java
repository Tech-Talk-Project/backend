package com.example.backend.board.controller;

import com.example.backend.board.dto.response.BoardPageResponseDto;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.service.boardView.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardViewController {
    private final ProjectBoardViewService projectBoardService;
    private final StudyBoardViewService studyBoardService;
    private final QuestionBoardViewService questionBoardService;
    private final PromotionBoardViewService promotionBoardService;
    private final FreeBoardViewService freeBoardService;

    @GetMapping("/board")
    public ResponseEntity<BoardViewResponseDto> viewProject(
            @RequestParam BoardCategory category,
            @RequestParam Long boardId ) {
        switch (category) {
            case PROJECT:
                log.info("PROJECT : viewProject api called");
                return ResponseEntity.ok(projectBoardService.getBoard(boardId));
            case STUDY:
                log.info("STUDY : viewProject api called");
                return ResponseEntity.ok(studyBoardService.getBoard(boardId));
            case QUESTION:
                log.info("QUESTION : viewProject api called");
                return ResponseEntity.ok(questionBoardService.getBoard(boardId));
            case PROMOTION:
                log.info("PROMOTION : viewProject api called");
                return ResponseEntity.ok(promotionBoardService.getBoard(boardId));
            case FREE:
                log.info("FREE : viewProject api called");
                return ResponseEntity.ok(freeBoardService.getBoard(boardId));
            default:
                log.warn("Invalid category: {}", category);
                return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/boards")
    public ResponseEntity<BoardPageResponseDto> getBoards(
            @RequestParam BoardCategory category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {

        page -= 1;
        switch (category) {
            case PROJECT:
                log.info("PROJECT : getBoards api called");
                return ResponseEntity.ok(projectBoardService.getBoards(page, size));
            case STUDY:
                log.info("STUDY : getBoards api called");
                return ResponseEntity.ok(studyBoardService.getBoards(page, size));
            case QUESTION:
                log.info("QUESTION : getBoards api called");
                return ResponseEntity.ok(questionBoardService.getBoards(page, size));
            case PROMOTION:
                log.info("PROMOTION : getBoards api called");
                return ResponseEntity.ok(promotionBoardService.getBoards(page, size));
            case FREE:
                log.info("FREE : getBoards api called");
                return ResponseEntity.ok(freeBoardService.getBoards(page, size));
            default:
                log.warn("Invalid category: {}", category);
                return ResponseEntity.badRequest().body(null);
        }
    }
}
