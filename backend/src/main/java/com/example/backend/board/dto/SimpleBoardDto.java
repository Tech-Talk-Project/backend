package com.example.backend.board.dto;

import com.example.backend.board.domain.*;
import com.example.backend.board.util.HtmlTextExtractor;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SimpleBoardDto {
    private String title;
    private String content;
    private SimpleMemberProfileDto author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tags;
    private Long viewCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private boolean recruitmentActive;

    @Builder
    public SimpleBoardDto(BoardEntity board, List<Tag> tags, Integer likeCount
            , Integer dislikeCount, Integer commentCount, boolean recruitmentActive) {
        this.title = board.getTitle();
        this.content = HtmlTextExtractor.extract(board.getContent());
        this.author = new SimpleMemberProfileDto(board.getAuthor());
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.viewCount = board.getViewCount();
        this.tags = tags.stream().map(Tag::getName).toList();
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.commentCount = commentCount;
        this.recruitmentActive = recruitmentActive;
    }

    public static List<SimpleBoardDto> ofFreeBoards(List<FreeBoard> freeBoards) {
        return freeBoards.stream()
                .map(board -> SimpleBoardDto.builder()
                        .board(board)
                        .tags(board.getTags())
                        .likeCount(board.getThumbsUps().size())
                        .dislikeCount(board.getThumbsDowns().size())
                        .commentCount(board.getComments().size())
                        .build())
                .toList();
    }

    public static List<SimpleBoardDto> ofProjectBoards(List<ProjectBoard> boards) {
        return boards.stream()
                .map(board -> SimpleBoardDto.builder()
                        .board(board)
                        .tags(board.getTags())
                        .likeCount(board.getThumbsUps().size())
                        .commentCount(board.getComments().size())
                        .recruitmentActive(board.isRecruitmentActive())
                        .build())
                .toList();
    }

    public static List<SimpleBoardDto> ofStudyBoards(List<StudyBoard> boards) {
        return boards.stream()
                .map(board -> SimpleBoardDto.builder()
                        .board(board)
                        .tags(board.getTags())
                        .likeCount(board.getThumbsUps().size())
                        .commentCount(board.getComments().size())
                        .recruitmentActive(board.isRecruitmentActive())
                        .build())
                .toList();
    }

    public static List<SimpleBoardDto> ofQuestionBoards(List<QuestionBoard> boards) {
        return boards.stream()
                .map(board -> SimpleBoardDto.builder()
                        .board(board)
                        .tags(board.getTags())
                        .likeCount(board.getThumbsUps().size())
                        .dislikeCount(board.getThumbsDowns().size())
                        .commentCount(board.getComments().size())
                        .build())
                .toList();
    }

    public static List<SimpleBoardDto> ofPromotionBoards(List<PromotionBoard> boards) {
        return boards.stream()
                .map(board -> SimpleBoardDto.builder()
                        .board(board)
                        .tags(board.getTags())
                        .likeCount(board.getThumbsUps().size())
                        .dislikeCount(board.getThumbsDowns().size())
                        .commentCount(board.getComments().size())
                        .build())
                .toList();
    }
}
