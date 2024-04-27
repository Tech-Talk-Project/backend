package com.example.backend.board.dto.response;

import com.example.backend.board.dto.SimpleBoardDto;
import lombok.Data;

import java.util.List;

@Data
public class BoardPageResponseDto {
    private List<SimpleBoardDto> boardList;
    private Integer totalPage;

    public BoardPageResponseDto(List<SimpleBoardDto> boardList, Integer totalPage) {
        this.boardList = boardList;
        this.totalPage = totalPage;
    }
}
