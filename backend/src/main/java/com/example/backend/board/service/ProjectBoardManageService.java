package com.example.backend.board.service;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.domain.Like;
import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.board.dto.request.*;
import com.example.backend.board.repository.LikeRepository;
import com.example.backend.board.repository.ProjectBoardRepository;
import com.example.backend.board.validator.BoardValidator;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectBoardManageService {
    private final ProjectBoardRepository projectBoardRepository;
    private final LikeRepository likeRepository;
    private final BoardValidator boardValidator;
    private final EntityManager em;

    public void createProject(Long memberId, ProjectCreateRequestDto dto) {
        Member member = em.getReference(Member.class, memberId);
        ProjectBoard projectBoard = new ProjectBoard(member, dto);
        projectBoardRepository.save(projectBoard);
    }


    public void updateProject(Long memberId, ProjectUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getProjectBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.update(dto);
    }

    public void removeProject(Long memberId, ProjectRemoveRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getProjectBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoardRepository.remove(projectBoard);
    }

    public void toggleRecruitment(Long memberId, ProjectUpdateRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getProjectBoardId());
        boardValidator.validateAuthor(memberId, projectBoard);
        projectBoard.toggleRecruitment();
    }

    public void addComment(Long memberId, CommentAddRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        projectBoard.addComment(new Comment(dto.getContent(), member));
    }

    public void addLike(Long memberId, LikeRequestDto dto) {
        ProjectBoard projectBoard = projectBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        projectBoard.addLike(new Like(member));
    }

    public void removeLike(Long memberId, LikeRequestDto dto) {
        likeRepository.removeProjectLikeByMemberId(dto.getBoardId(), memberId);
    }
}
