package com.example.backend.board.controller;

import com.example.backend.board.dto.request.CommentAddRequestDto;
import com.example.backend.board.dto.request.ProjectCreateRequestDto;
import com.example.backend.board.dto.request.ProjectRemoveRequestDto;
import com.example.backend.board.dto.request.ProjectUpdateRequestDto;
import com.example.backend.board.service.ProjectBoardManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/project")
public class ProjectBoardManageController {
    private final ProjectBoardManageService projectBoardManageService;

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody ProjectCreateRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.createProject(memberId, dto);
        return ResponseEntity.ok("프로젝트 생성 완료");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProject(
            @RequestBody ProjectUpdateRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.updateProject(memberId, dto);
        return ResponseEntity.ok("프로젝트 수정 완료");
    }

    @PostMapping("/toggle-recruitment")
    public ResponseEntity<String> toggleRecruitment(
            @RequestBody ProjectUpdateRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.toggleRecruitment(memberId, dto);
        return ResponseEntity.ok("모집 상태 변경 완료");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteProject(
            @RequestBody ProjectRemoveRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.removeProject(memberId, dto);
        return ResponseEntity.ok("프로젝트 삭제 완료");
    }

    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(
            @RequestBody CommentAddRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectBoardManageService.addComment(memberId, dto);
        return ResponseEntity.ok("댓글이 추가되었습니다.");
    }
}
