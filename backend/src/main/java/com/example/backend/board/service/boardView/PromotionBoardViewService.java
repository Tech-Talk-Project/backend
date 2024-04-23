package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.PromotionBoard;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.repository.PromotionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionBoardViewService implements BoardViewService{
    private final PromotionBoardRepository promotionBoardRepository;

    @Override
    @Transactional
    public BoardViewResponseDto view(Long boardId) {
        PromotionBoard promotionBoard = promotionBoardRepository.findByIdWithAll(boardId);
        promotionBoard.increaseViewCount();
        return new BoardViewResponseDto(promotionBoard);
    }
}
