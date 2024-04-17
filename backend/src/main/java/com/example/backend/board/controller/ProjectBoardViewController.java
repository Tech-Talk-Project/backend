package com.example.backend.board.controller;

import com.example.backend.board.dto.response.ProjectViewResponseDto;
import com.example.backend.board.service.ProjectBoardViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectBoardViewController {
    private final ProjectBoardViewService projectBoardService;

    @GetMapping("/project")
    public ResponseEntity<ProjectViewResponseDto> viewProject(
            @RequestParam Long projectBoardId ) {
        return ResponseEntity.ok(projectBoardService.viewProject(projectBoardId));
    }
}
