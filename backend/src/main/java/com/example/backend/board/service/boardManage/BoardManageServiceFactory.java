package com.example.backend.board.service.boardManage;

import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.service.boardView.BoardViewService;
import com.example.backend.board.service.boardView.ProjectBoardViewService;
import com.example.backend.board.service.boardView.StudyBoardViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BoardManageServiceFactory {
    private final ApplicationContext context;

    public BoardManageService get(BoardCategory boardCategory) {
        switch (boardCategory) {
            case PROJECT:
                return context.getBean(ProjectBoardManageService.class);
            case STUDY:
                return context.getBean(StudyBoardManageService.class);
            case QUESTION:
                return context.getBean(QuestionBoardManageService.class);
            case PROMOTION:
                return context.getBean(PromotionBoardManageService.class);
            case FREE:
                return context.getBean(FreeBoardManageService.class);
            default:
                throw new IllegalArgumentException("Invalid category: " + boardCategory);
        }
    }


    public BoardManageService getRecruitBoard(BoardCategory boardCategory) {
        switch (boardCategory) {
            case PROJECT:
                return context.getBean(ProjectBoardManageService.class);
            case STUDY:
                return context.getBean(StudyBoardManageService.class);
            default:
                throw new IllegalArgumentException("Invalid category: " + boardCategory);
        }
    }
}
