package com.example.backend.board.dto.response;

import com.example.backend.board.domain.StudyBoard;
import com.example.backend.board.dto.CommentDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.domain.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudyBoardViewResponseDto extends BoardViewResponseDto{
    private boolean recruitmentActive;

    public StudyBoardViewResponseDto(StudyBoard studyBoard) {
        super(studyBoard);
        this.category = BoardCategory.STUDY;
        this.boardId = studyBoard.getId();
        this.likeCount = studyBoard.getThumbsUps().size();
        this.comments = CommentDto.listOf(studyBoard.getComments());
        this.tags = studyBoard.getTags().stream().map(Tag::getName).toList();

        this.recruitmentActive = studyBoard.isRecruitmentActive();
    }
}
