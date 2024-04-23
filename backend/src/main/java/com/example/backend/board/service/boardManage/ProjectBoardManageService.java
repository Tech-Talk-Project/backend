package com.example.backend.board.service.boardManage;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.domain.ThumbsUp;
import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.dto.request.*;
import com.example.backend.board.repository.ProjectBoardRepository;
import com.example.backend.board.validator.BoardValidator;
import com.example.backend.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectBoardManageService implements BoardManageService{
    private final ProjectBoardRepository projectBoardRepository;
    private final BoardValidator boardValidator;
    private final EntityManager em;

    @Override
    public Long create(Long memberId, BoardCreateRequestDto dto) {
        Member member = em.getReference(Member.class, memberId);
        ProjectBoard projectBoard = new ProjectBoard(member, dto);
        projectBoardRepository.save(projectBoard);
        return projectBoard.getId();
    }

    @Override
    public void update(Long memberId, BoardUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.update(dto);
    }

    @Override
    public void delete(Long memberId, BoardDeleteRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoardRepository.remove(projectBoard);
    }

    @Override
    public void addComment(Long memberId, CommentAddRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        projectBoard.addComment(new Comment(dto.getContent(), member));
    }

    @Override
    public boolean toggleLike(Long memberId, LikeRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        if (existLike(memberId, projectBoard)) {
            removeLike(memberId, projectBoard);
            return false;
        } else {
            addLike(memberId, projectBoard);
            return true;
        }
    }

    @Override
    public boolean checkLike(Long memberId, Long boardId) {
        ProjectBoard projectBoard = projectBoardRepository.findById(boardId);
        return existLike(memberId, projectBoard);
    }

    @Override
    public boolean toggleDislike(Long memberId, LikeRequestDto dto) {
        return false;
    }

    @Override
    public boolean checkDislike(Long memberId, Long boardId) {
        return false;
    }

    public void toggleRecruitment(Long memberId, BoardUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.toggleRecruitment();
    }

    private boolean existLike(Long memberId, ProjectBoard projectBoard) {
        return projectBoard.getThumbsUps().stream()
                .anyMatch(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    private void removeLike(Long memberId, ProjectBoard projectBoard) {
        projectBoard.getThumbsUps().removeIf(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    private void addLike(Long memberId, ProjectBoard projectBoard) {
        Member member = em.getReference(Member.class, memberId);
        projectBoard.addThumbsUp(new ThumbsUp(member));
    }
}
