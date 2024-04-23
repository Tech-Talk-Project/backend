package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.FreeBoard;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.repository.FreeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreeBoardViewService implements BoardViewService{
    private final FreeBoardRepository freeBoardRepository;

    @Override
    @Transactional
    public BoardViewResponseDto view(Long boardId) {
        FreeBoard freeBoard = freeBoardRepository.findByIdWithAll(boardId);
        freeBoard.increaseViewCount();
        return new BoardViewResponseDto(freeBoard);
    }
}
