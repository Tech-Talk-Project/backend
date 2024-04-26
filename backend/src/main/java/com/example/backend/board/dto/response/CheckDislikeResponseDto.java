package com.example.backend.board.dto.response;

import lombok.Data;

@Data
public class CheckDislikeResponseDto {
    private boolean disliked;

    public CheckDislikeResponseDto(boolean disliked) {
        this.disliked = disliked;
    }
}
