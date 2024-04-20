package com.example.backend.board.dto.request;

import com.example.backend.board.service.BoardCategory;
import lombok.Data;

import java.util.List;

@Data
public class BoardUpdateRequestDto {
    private BoardCategory category;
    private Long boardId;
}
