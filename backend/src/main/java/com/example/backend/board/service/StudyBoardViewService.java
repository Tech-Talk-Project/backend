package com.example.backend.board.service;

import com.example.backend.board.domain.StudyBoard;
import com.example.backend.board.dto.response.StudyBoardViewResponseDto;
import com.example.backend.board.repository.StudyBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardViewService {
    private final StudyBoardRepository studyBoardRepository;

    public StudyBoardViewResponseDto viewStudy(Long studyBoardId) {
        StudyBoard studyBoard = studyBoardRepository.findByIdWithAll(studyBoardId);
        return new StudyBoardViewResponseDto(studyBoard);
    }


    @Transactional
    public void increaseViewCount(Long boardId) {
        studyBoardRepository.increaseViewCount(boardId);
    }
}
