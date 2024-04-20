package com.example.backend.board.dto.response;

import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.domain.Tag;
import com.example.backend.board.dto.CommentDto;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectViewResponseDto {
    private Long projectBoardId;
    private String title;
    private SimpleMemberProfileDto author;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    private int likeCount;
    private boolean recruitmentActive;
    private List<CommentDto> comments;
    private List<String> tags;

    public ProjectViewResponseDto(ProjectBoard projectBoard) {
        this.projectBoardId = projectBoard.getId();
        this.title = projectBoard.getTitle();
        this.author = new SimpleMemberProfileDto(projectBoard.getAuthor());
        this.content = projectBoard.getContent();
        this.createdAt = projectBoard.getCreatedAt();
        this.updatedAt = projectBoard.getUpdatedAt();
        this.viewCount = projectBoard.getViewCount();
        this.likeCount = projectBoard.getThumbsUps().size();
        this.recruitmentActive = projectBoard.isRecruitmentActive();
        this.comments = CommentDto.listOf(projectBoard.getComments());
        this.tags = projectBoard.getTags().stream().map(Tag::getName).toList();
    }
}
