package com.example.backend.board.dto.request;

import com.example.backend.board.service.BoardCategory;
import lombok.Data;

@Data
public class BoardViewIncreaseRequestDto {
    BoardCategory category;
    Long boardId;
}
