package com.example.backend.board.controller;

import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.service.ProjectBoardViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardViewController {
    private final ProjectBoardViewService projectBoardService;

    @GetMapping("/project")
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
}
