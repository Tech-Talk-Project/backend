package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.StudyBoard;
import com.example.backend.board.dto.response.StudyBoardViewResponseDto;
import com.example.backend.board.repository.StudyBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardViewService implements BoardViewService{
    private final StudyBoardRepository studyBoardRepository;

    @Override
    @Transactional
    public StudyBoardViewResponseDto view(Long studyBoardId) {
        StudyBoard studyBoard = studyBoardRepository.findByIdWithAll(studyBoardId);
        studyBoard.increaseViewCount();
        return new StudyBoardViewResponseDto(studyBoard);
    }
}
