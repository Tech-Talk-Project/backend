package com.example.backend.board.dto.request;

import lombok.Data;

@Data
public class CommentUpdateRequestDto {
    private Long commentId;
    private String content;
}
