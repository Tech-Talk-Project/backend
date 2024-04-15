package com.example.backend.board.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProjectCreateRequestDto {
    private String title;
    private String content;
    private String recruitPosition;
    private List<String> tags;
}
