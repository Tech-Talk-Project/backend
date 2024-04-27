package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.StudyBoard;
import com.example.backend.board.dto.SimpleBoardDto;
import com.example.backend.board.dto.response.BoardPageResponseDto;
import com.example.backend.board.dto.response.StudyBoardViewResponseDto;
import com.example.backend.board.repository.StudyBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardViewService implements BoardViewService{
    private final StudyBoardRepository studyBoardRepository;

    @Override
    @Transactional
    public StudyBoardViewResponseDto getBoard(Long studyBoardId) {
        StudyBoard studyBoard = studyBoardRepository.findByIdWithAll(studyBoardId);
        studyBoard.increaseViewCount();
        return new StudyBoardViewResponseDto(studyBoard);
    }

    @Override
    public BoardPageResponseDto getBoards(int page, int size) {
        List<StudyBoard> boards = studyBoardRepository.findAll(page, size);
        Long totalCount = studyBoardRepository.countAll();
        int totalPage = (int) Math.ceil((double) totalCount / size);
        List<SimpleBoardDto> simpleBoards = SimpleBoardDto.ofStudyBoards(boards);
        return new BoardPageResponseDto(simpleBoards, totalPage);
    }
}
