package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.QuestionBoard;
import com.example.backend.board.dto.SimpleBoardDto;
import com.example.backend.board.dto.response.BoardPageResponseDto;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.repository.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionBoardViewService implements BoardViewService {
    private final QuestionBoardRepository questionBoardRepository;

    @Override
    @Transactional
    public BoardViewResponseDto getBoard(Long questionBoardId) {
        QuestionBoard questionBoard = questionBoardRepository.findByIdWithAll(questionBoardId);
        questionBoard.increaseViewCount();
        return new BoardViewResponseDto(questionBoard);
    }

    @Override
    public BoardPageResponseDto getBoards(int page, int size) {
        List<QuestionBoard> boards = questionBoardRepository.findAll(page, size);
        Long totalCount = questionBoardRepository.countAll();
        int totalPage = (int) Math.ceil((double) totalCount / size);
        List<SimpleBoardDto> simpleBoards = SimpleBoardDto.ofQuestionBoards(boards);
        return new BoardPageResponseDto(simpleBoards, totalPage);
    }
}
