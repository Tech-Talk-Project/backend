package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.dto.response.ProjectBoardViewResponseDto;
import com.example.backend.board.repository.ProjectBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectBoardViewService implements BoardViewService{
    private final ProjectBoardRepository projectBoardRepository;

    @Override
    @Transactional
    public ProjectBoardViewResponseDto view(Long projectBoardId) {
        ProjectBoard projectBoard = projectBoardRepository.findByIdWithAll(projectBoardId);
        projectBoard.increaseViewCount();
        return new ProjectBoardViewResponseDto(projectBoard);
    }
}
