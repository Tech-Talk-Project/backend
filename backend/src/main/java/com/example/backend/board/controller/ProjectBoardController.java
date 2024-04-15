package com.example.backend.board.controller;

import com.example.backend.board.dto.request.ProjectCreateRequestDto;
import com.example.backend.board.dto.response.ProjectViewResponseDto;
import com.example.backend.board.service.ProjectBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/board/project")
public class ProjectBoardController {
    private final ProjectBoardService projectBoardService;

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody ProjectCreateRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardService.createProject(memberId, dto);
        return ResponseEntity.ok("프로젝트 생성 완료");
    }

    @GetMapping("/view")
    public ResponseEntity<ProjectViewResponseDto> viewProject(
            @RequestParam Long projectBoardId ) {

    }


}
