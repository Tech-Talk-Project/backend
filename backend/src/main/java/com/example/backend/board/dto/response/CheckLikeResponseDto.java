package com.example.backend.board.dto.response;

import lombok.Data;

@Data
public class CheckLikeResponseDto {
    private boolean isLiked;

    public CheckLikeResponseDto(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
