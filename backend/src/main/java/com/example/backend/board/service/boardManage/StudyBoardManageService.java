package com.example.backend.board.service.boardManage;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.domain.StudyBoard;
import com.example.backend.board.domain.ThumbsUp;
import com.example.backend.board.dto.request.*;
import com.example.backend.board.repository.StudyBoardRepository;
import com.example.backend.board.validator.BoardValidator;
import com.example.backend.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyBoardManageService implements BoardManageService{
    private final StudyBoardRepository studyBoardRepository;
    private final BoardValidator boardValidator;
    private final EntityManager em;

    @Override
    public Long create(Long memberId, BoardCreateRequestDto dto) {
        Member member = em.getReference(Member.class, memberId);
        StudyBoard studyBoard = new StudyBoard(member, dto);
        studyBoardRepository.save(studyBoard);
        return studyBoard.getId();
    }

    @Override
    public void update(Long memberId, BoardUpdateRequestDto dto) {
        StudyBoard studyBoard = studyBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, studyBoard);
        studyBoard.update(dto);
    }

    @Override
    public void delete(Long memberId, BoardDeleteRequestDto dto) {
        StudyBoard studyBoard = studyBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, studyBoard);
        studyBoardRepository.remove(studyBoard);
    }

    @Override
    public void addComment(Long memberId, CommentAddRequestDto dto) {
        StudyBoard studyBoard = studyBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        studyBoard.addComment(new Comment(dto.getContent(), member));
    }

    @Override
    public boolean toggleLike(Long memberId, LikeRequestDto dto) {
        StudyBoard studyBoard = studyBoardRepository.findById(dto.getBoardId());
        if (existLike(memberId, studyBoard)) {
            removeLike(memberId, studyBoard);
            return false;
        } else {
            addLike(memberId, studyBoard);
            return true;
        }
    }

    private void addLike(Long memberId, StudyBoard studyBoard) {
        Member member = em.getReference(Member.class, memberId);
        studyBoard.addThumbsUp(new ThumbsUp(member));
    }

    private void removeLike(Long memberId, StudyBoard studyBoard) {
        studyBoard.getThumbsUps().removeIf(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    private boolean existLike(Long memberId, StudyBoard studyBoard) {
        return studyBoard.getThumbsUps().stream()
                .anyMatch(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    @Override
    public boolean checkLike(Long memberId, Long boardId) {
        StudyBoard studyBoard = studyBoardRepository.findById(boardId);
        return existLike(memberId, studyBoard);
    }

    @Override
    public boolean toggleDislike(Long memberId, LikeRequestDto dto) {
        return false;
    }

    @Override
    public boolean checkDislike(Long memberId, Long boardId) {
        return false;
    }

    @Override
    public void toggleRecruitment(Long memberId, BoardUpdateRequestDto dto) {
        StudyBoard studyBoard = studyBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, studyBoard);
        studyBoard.toggleRecruitment();
    }
}
