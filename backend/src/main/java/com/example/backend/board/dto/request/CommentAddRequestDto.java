package com.example.backend.board.dto.request;

import com.example.backend.board.service.BoardCategory;
import lombok.Data;

@Data
public class CommentAddRequestDto {
    private BoardCategory category;
    private Long boardId;
    private String content;
}
