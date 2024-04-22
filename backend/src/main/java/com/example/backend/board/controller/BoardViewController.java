package com.example.backend.board.controller;

import com.example.backend.board.dto.request.BoardViewIncreaseRequestDto;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.service.ProjectBoardViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardViewController {
    private final ProjectBoardViewService projectBoardService;

    @GetMapping("/board")
    public ResponseEntity<BoardViewResponseDto> viewProject(
            @RequestParam BoardCategory category,
            @RequestParam Long boardId ) {
        switch (category) {
            case PROJECT:
                log.info("PROJECT : viewProject api called");
                return ResponseEntity.ok(projectBoardService.viewProject(boardId));
            case STUDY:
                log.info("STUDY : viewProject api called");
                break;
            case QUESTION:
                log.info("QUESTION : viewProject api called");
                break;
            case PROMOTION:
                log.info("PROMOTION : viewProject api called");
                break;
            case FREE:
                log.info("FREE : viewProject api called");
                break;
            default:
                log.warn("Invalid category: {}", category);
                return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/board/view")
    public ResponseEntity<String> increaseViewCount(
            @RequestBody BoardViewIncreaseRequestDto dto) {
        BoardCategory category = dto.getCategory();
        Long boardId = dto.getBoardId();
        switch (category) {
            case PROJECT:
                log.info("PROJECT : increaseViewCount api called");
                projectBoardService.increaseViewCount(boardId);
                return ResponseEntity.ok("조회수 증가 완료");
            case STUDY:
                log.info("STUDY : increaseViewCount api called");
                break;
            case QUESTION:
                log.info("QUESTION : increaseViewCount api called");
                break;
            case PROMOTION:
                log.info("PROMOTION : increaseViewCount api called");
                break;
            case FREE:
                log.info("FREE : increaseViewCount api called");
                break;
            default:
                log.warn("Invalid category: {}", category);
                return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
