package com.example.backend.board.dto.response;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.dto.CommentDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardViewResponseDto {
    protected BoardCategory category;
    protected Long boardId;
    protected String title;
    protected SimpleMemberProfileDto author;
    protected String content;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected Long viewCount;
    protected int likeCount;
    protected int dislikeCount;
    protected List<CommentDto> comments;
    protected List<String> tags;

    public BoardViewResponseDto(BoardEntity boardEntity){
        this.title = boardEntity.getTitle();
        this.author = new SimpleMemberProfileDto(boardEntity.getAuthor());
        this.content = boardEntity.getContent();
        this.createdAt = boardEntity.getCreatedAt();
        this.updatedAt = boardEntity.getUpdatedAt();
        this.viewCount = boardEntity.getViewCount();
    }

}
