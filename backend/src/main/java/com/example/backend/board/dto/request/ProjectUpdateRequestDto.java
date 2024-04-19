package com.example.backend.board.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProjectUpdateRequestDto {
    private Long projectBoardId;
    private String title;
    private String content;
    private List<String> tags;
}
