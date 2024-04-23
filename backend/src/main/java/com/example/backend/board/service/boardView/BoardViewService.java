package com.example.backend.board.service.boardView;

import com.example.backend.board.dto.response.BoardViewResponseDto;

public interface BoardViewService {
    BoardViewResponseDto view(Long boardId);

}
