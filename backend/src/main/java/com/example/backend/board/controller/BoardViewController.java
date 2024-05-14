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
    private final BoardViewServiceFactory boardViewServiceFactory;

    @GetMapping("/board")
    public ResponseEntity<BoardViewResponseDto> viewProject(
            @RequestParam BoardCategory category,
            @RequestParam Long boardId ) {
        log.info("viewProject called : category={}, boardId={}", category, boardId);
        BoardViewService boardViewService = boardViewServiceFactory.get(category);
        return ResponseEntity.ok(boardViewService.getBoard(boardId));
    }

    @GetMapping("/boards")
    public ResponseEntity<BoardPageResponseDto> getBoards(
            @RequestParam BoardCategory category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {

        log.info("getBoards called : category={}, page={}, size={}", category, page, size);
        page -= 1;
        BoardViewService boardViewService = boardViewServiceFactory.get(category);
        return ResponseEntity.ok(boardViewService.getBoards(page, size));
    }
}
