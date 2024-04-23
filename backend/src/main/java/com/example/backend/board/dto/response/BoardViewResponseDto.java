package com.example.backend.board.dto.response;

import com.example.backend.board.domain.*;
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

    public BoardViewResponseDto(QuestionBoard questionBoard){
        this.category = BoardCategory.QUESTION;
        this.boardId = questionBoard.getId();
        this.title = questionBoard.getTitle();
        this.author = new SimpleMemberProfileDto(questionBoard.getAuthor());
        this.content = questionBoard.getContent();
        this.createdAt = questionBoard.getCreatedAt();
        this.updatedAt = questionBoard.getUpdatedAt();
        this.viewCount = questionBoard.getViewCount();
        this.likeCount = questionBoard.getThumbsUps().size();
        this.dislikeCount = questionBoard.getThumbsDowns().size();
        this.comments = CommentDto.listOf(questionBoard.getComments());
        this.tags = questionBoard.getTags().stream().map(Tag::getName).toList();
    }

    public BoardViewResponseDto(PromotionBoard promotionBoard){
        this.category = BoardCategory.PROMOTION;
        this.boardId = promotionBoard.getId();
        this.title = promotionBoard.getTitle();
        this.author = new SimpleMemberProfileDto(promotionBoard.getAuthor());
        this.content = promotionBoard.getContent();
        this.createdAt = promotionBoard.getCreatedAt();
        this.updatedAt = promotionBoard.getUpdatedAt();
        this.viewCount = promotionBoard.getViewCount();
        this.likeCount = promotionBoard.getThumbsUps().size();
        this.dislikeCount = promotionBoard.getThumbsDowns().size();
        this.comments = CommentDto.listOf(promotionBoard.getComments());
        this.tags = promotionBoard.getTags().stream().map(Tag::getName).toList();
    }

    public BoardViewResponseDto(FreeBoard freeBoard) {
        this.category = BoardCategory.FREE;
        this.boardId = freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.author = new SimpleMemberProfileDto(freeBoard.getAuthor());
        this.content = freeBoard.getContent();
        this.createdAt = freeBoard.getCreatedAt();
        this.updatedAt = freeBoard.getUpdatedAt();
        this.viewCount = freeBoard.getViewCount();
        this.likeCount = freeBoard.getThumbsUps().size();
        this.dislikeCount = freeBoard.getThumbsDowns().size();
        this.comments = CommentDto.listOf(freeBoard.getComments());
        this.tags = freeBoard.getTags().stream().map(Tag::getName).toList();
    }
}
