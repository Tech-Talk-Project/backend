package com.example.backend.board.dto.request;

import lombok.Data;

@Data
public class CommentAddRequestDto {
    private Long boardId;
    private String content;
}
