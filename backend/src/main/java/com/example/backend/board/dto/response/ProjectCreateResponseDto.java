package com.example.backend.board.dto.response;

import lombok.Data;

@Data
public class ProjectCreateResponseDto {
    private Long projectId;

    public ProjectCreateResponseDto(Long projectId) {
        this.projectId = projectId;
    }
}
