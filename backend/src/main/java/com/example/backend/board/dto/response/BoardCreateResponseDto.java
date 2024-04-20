package com.example.backend.board.dto.response;

import com.example.backend.board.service.BoardCategory;
import lombok.Data;

@Data
public class BoardCreateResponseDto {
    private BoardCategory category;
    private Long boardId;

    public BoardCreateResponseDto(BoardCategory category, Long boardId) {
        this.category = category;
        this.boardId = boardId;
    }
}
