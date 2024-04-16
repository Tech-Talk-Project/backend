package com.example.backend.board.dto;

import com.example.backend.board.domain.Comment;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {
    private SimpleMemberProfileDto author;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentDto(Comment comment) {
        this.author = new SimpleMemberProfileDto(comment.getAuthor());
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    public static List<CommentDto> listOf(List<Comment> comments) {
        return comments.stream().map(CommentDto::new).toList();
    }
}
