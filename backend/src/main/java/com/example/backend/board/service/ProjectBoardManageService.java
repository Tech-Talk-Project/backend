package com.example.backend.board.service;

import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.dto.request.ProjectCreateRequestDto;
import com.example.backend.board.dto.request.ProjectRemoveRequestDto;
import com.example.backend.board.dto.request.ProjectUpdateRequestDto;
import com.example.backend.board.dto.response.ProjectViewResponseDto;
import com.example.backend.board.repository.ProjectBoardRepository;
import com.example.backend.board.validator.BoardValidator;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectBoardManageService {
    private final ProjectBoardRepository projectBoardRepository;
    private final MemberRepository memberRepository;
    private final BoardValidator boardValidator;

    public void createProject(Long memberId, ProjectCreateRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        ProjectBoard projectBoard = new ProjectBoard(member, dto);
        projectBoardRepository.save(projectBoard);
    }


    public void updateProject(Long memberId, ProjectUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findByIdWithAll(dto.getProjectBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.update(dto);
    }

    public void removeProject(Long memberId, ProjectRemoveRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findByIdWithAll(dto.getProjectBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoardRepository.remove(projectBoard);
    }

    public void toggleRecruitment(Long memberId, ProjectUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findByIdWithAll(dto.getProjectBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.toggleRecruitment();
    }
}
