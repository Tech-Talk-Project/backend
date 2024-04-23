package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.QuestionBoard;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionBoardViewService implements BoardViewService {
    private final QuestionBoardRepository questionBoardRepository;

    @Override
    @Transactional
    public BoardViewResponseDto view(Long questionBoardId) {
        QuestionBoard questionBoard = questionBoardRepository.findByIdWithAll(questionBoardId);
        questionBoard.increaseViewCount();
        return new BoardViewResponseDto(questionBoard);
    }
}
