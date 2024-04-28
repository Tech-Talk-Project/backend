package com.example.backend.board.dto.request;

import com.example.backend.board.service.BoardCategory;
import lombok.Data;

import java.util.List;

@Data
public class BoardCreateRequestDto {
    private BoardCategory category;
    private String title;
    private String content;
    private List<String> tags;

    public BoardCreateRequestDto (BoardCategory category, String title) {
        this.category = category;
        this.title = title;
        this.content = "test";
        this.tags = List.of("test1", "test2");
    }
}
