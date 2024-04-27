package com.example.backend.board.dto.response;

import lombok.Data;

@Data
public class CheckLikeResponseDto {
    private boolean liked;

    public CheckLikeResponseDto(boolean liked) {
        this.liked = liked;
    }
}
