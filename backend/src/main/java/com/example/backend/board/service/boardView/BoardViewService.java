package com.example.backend.board.service.boardView;

import com.example.backend.board.dto.response.BoardPageResponseDto;
import com.example.backend.board.dto.response.BoardViewResponseDto;

public interface BoardViewService {
    BoardViewResponseDto getBoard(Long boardId);

    BoardPageResponseDto getBoards(int page, int size);
}
