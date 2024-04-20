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
}
