package com.example.backend.board.dto.request;

import com.example.backend.board.service.BoardCategory;
import lombok.Data;

@Data
public class LikeRequestDto {
    private BoardCategory category;
    private Long boardId;
}
