package com.example.backend.board.service.boardView;


import com.example.backend.board.service.BoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BoardViewServiceFactory {
    private final ApplicationContext context;

    public BoardViewService get(BoardCategory boardCategory) {
        switch (boardCategory) {
            case PROJECT:
                return context.getBean(ProjectBoardViewService.class);
            case STUDY:
                return context.getBean(StudyBoardViewService.class);
            case QUESTION:
                return context.getBean(QuestionBoardViewService.class);
            case PROMOTION:
                return context.getBean(PromotionBoardViewService.class);
            case FREE:
                return context.getBean(FreeBoardViewService.class);
            default:
                throw new IllegalArgumentException("Invalid category: " + boardCategory);
        }
    }
}
