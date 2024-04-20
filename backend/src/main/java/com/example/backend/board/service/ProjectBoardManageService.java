package com.example.backend.board.service;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.domain.ThumbsUp;
import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.dto.request.*;
import com.example.backend.board.repository.ThumbsUpRepository;
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
public class ProjectBoardManageService {
    private final ProjectBoardRepository projectBoardRepository;
    private final ThumbsUpRepository thumbsUpRepository;
    private final BoardValidator boardValidator;
    private final EntityManager em;

    public Long createProject(Long memberId, BoardCreateRequestDto dto) {
        Member member = em.getReference(Member.class, memberId);
        ProjectBoard projectBoard = new ProjectBoard(member, dto);
        projectBoardRepository.save(projectBoard);
        return projectBoard.getId();
    }


    public void updateProject(Long memberId, BoardUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.update(dto);
    }

    public void removeProject(Long memberId, ProjectRemoveRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getProjectBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoardRepository.remove(projectBoard);
    }

    public void toggleRecruitment(Long memberId, BoardUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.toggleRecruitment();
    }

    public void addComment(Long memberId, CommentAddRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        projectBoard.addComment(new Comment(dto.getContent(), member));
    }

    /**
     * 좋아요 토글 <br>
     * 이미 좋아요가 눌려있으면 좋아요 취소 후 false 반환, 아니면 좋아요 추가 후 true 반환
     * @param memberId
     * @param dto
     * @return
     */
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

    public boolean checkLike(Long memberId, Long boardId) {
        ProjectBoard projectBoard = projectBoardRepository.findById(boardId);
        return existLike(memberId, projectBoard);
    }

    private boolean existLike(Long memberId, ProjectBoard projectBoard) {
        return projectBoard.getThumbsUps().stream().anyMatch(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    private void removeLike(Long memberId, ProjectBoard projectBoard) {
        projectBoard.getThumbsUps().removeIf(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    private void addLike(Long memberId, ProjectBoard projectBoard) {
        Member member = em.getReference(Member.class, memberId);
        projectBoard.addThumbsUp(new ThumbsUp(member));
    }
}
