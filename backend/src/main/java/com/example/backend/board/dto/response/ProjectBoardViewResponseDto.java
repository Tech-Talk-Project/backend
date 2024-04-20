package com.example.backend.board.dto.response;

import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.dto.CommentDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.domain.Tag;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjectBoardViewResponseDto extends BoardViewResponseDto{
    private boolean recruitmentActive;
    public ProjectBoardViewResponseDto(ProjectBoard projectBoard) {
        super(projectBoard);
        this.category = BoardCategory.PROJECT;
        this.boardId = projectBoard.getId();
        this.likeCount = projectBoard.getThumbsUps().size();
        this.comments = CommentDto.listOf(projectBoard.getComments());
        this.tags = projectBoard.getTags().stream().map(Tag::getName).toList();

        this.recruitmentActive = projectBoard.isRecruitmentActive();
    }
}
