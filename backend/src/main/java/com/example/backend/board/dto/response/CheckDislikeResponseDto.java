package com.example.backend.board.dto.response;

import lombok.Data;

@Data
public class CheckDislikeResponseDto {
    private boolean isDisliked;

    public CheckDislikeResponseDto(boolean isDisliked) {
        this.isDisliked = isDisliked;
    }
}
