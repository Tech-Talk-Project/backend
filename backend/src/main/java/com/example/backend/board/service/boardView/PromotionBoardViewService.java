package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.PromotionBoard;
import com.example.backend.board.dto.SimpleBoardDto;
import com.example.backend.board.dto.response.BoardPageResponseDto;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.repository.PromotionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionBoardViewService implements BoardViewService{
    private final PromotionBoardRepository promotionBoardRepository;

    @Override
    @Transactional
    public BoardViewResponseDto getBoard(Long boardId) {
        PromotionBoard promotionBoard = promotionBoardRepository.findByIdWithAll(boardId);
        promotionBoard.increaseViewCount();
        return new BoardViewResponseDto(promotionBoard);
    }

    @Override
    public BoardPageResponseDto getBoards(int page, int size) {
        List<PromotionBoard> boards = promotionBoardRepository.findAll(page, size);
        Long totalCount = promotionBoardRepository.countAll();
        int totalPage = (int) Math.ceil((double) totalCount / size);
        List<SimpleBoardDto> simpleBoards = SimpleBoardDto.ofPromotionBoards(boards);
        return new BoardPageResponseDto(simpleBoards, totalPage);
    }
}
