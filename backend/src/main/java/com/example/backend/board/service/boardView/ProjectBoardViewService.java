package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.dto.SimpleBoardDto;
import com.example.backend.board.dto.response.BoardPageResponseDto;
import com.example.backend.board.dto.response.ProjectBoardViewResponseDto;
import com.example.backend.board.repository.ProjectBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectBoardViewService implements BoardViewService{
    private final ProjectBoardRepository projectBoardRepository;

    @Override
    @Transactional
    public ProjectBoardViewResponseDto getBoard(Long projectBoardId) {
        ProjectBoard projectBoard = projectBoardRepository.findByIdWithAll(projectBoardId);
        projectBoard.increaseViewCount();
        return new ProjectBoardViewResponseDto(projectBoard);
    }

    @Override
    public BoardPageResponseDto getBoards(int page, int size) {
        List<ProjectBoard> boards = projectBoardRepository.findAll(page, size);
        Long totalCount = projectBoardRepository.countAll();
        int totalPage = (int) Math.ceil((double) totalCount / size);
        List<SimpleBoardDto> simpleBoards = SimpleBoardDto.ofProjectBoards(boards);
        return new BoardPageResponseDto(simpleBoards, totalPage);
    }
}
