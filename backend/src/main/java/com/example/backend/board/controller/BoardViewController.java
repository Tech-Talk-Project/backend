package com.example.backend.board.controller;

import com.example.backend.board.dto.request.BoardViewIncreaseRequestDto;
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
                return ResponseEntity.ok(projectBoardService.view(boardId));
            case STUDY:
                log.info("STUDY : viewProject api called");
                return ResponseEntity.ok(studyBoardService.view(boardId));
            case QUESTION:
                log.info("QUESTION : viewProject api called");
                return ResponseEntity.ok(questionBoardService.view(boardId));
            case PROMOTION:
                log.info("PROMOTION : viewProject api called");
                return ResponseEntity.ok(promotionBoardService.view(boardId));
            case FREE:
                log.info("FREE : viewProject api called");
                return ResponseEntity.ok(freeBoardService.view(boardId));
            default:
                log.warn("Invalid category: {}", category);
                return ResponseEntity.badRequest().body(null);
        }
    }
}
