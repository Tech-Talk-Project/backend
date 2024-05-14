package com.example.backend.board.service.boardManage;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.domain.QuestionBoard;
import com.example.backend.board.domain.ThumbsDown;
import com.example.backend.board.domain.ThumbsUp;
import com.example.backend.board.dto.request.*;
import com.example.backend.board.repository.QuestionBoardRepository;
import com.example.backend.board.validator.BoardValidator;
import com.example.backend.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionBoardManageService implements BoardManageService{
    private final QuestionBoardRepository questionBoardRepository;
    private final BoardValidator boardValidator;
    private final EntityManager em;
    @Override
    public Long create(Long memberId, BoardCreateRequestDto dto) {
        Member member = em.getReference(Member.class, memberId);
        QuestionBoard questionBoard = new QuestionBoard(member, dto);
        questionBoardRepository.save(questionBoard);
        return questionBoard.getId();
    }

    @Override
    public void update(Long memberId, BoardUpdateRequestDto dto) {
        QuestionBoard questionBoard = questionBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, questionBoard);
        questionBoard.update(dto);
    }

    @Override
    public void delete(Long memberId, BoardDeleteRequestDto dto) {
        QuestionBoard questionBoard = questionBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, questionBoard);
        questionBoardRepository.remove(questionBoard);
    }

    @Override
    public void addComment(Long memberId, CommentAddRequestDto dto) {
        QuestionBoard questionBoard = questionBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        questionBoard.addComment(new Comment(dto.getContent(), member));
    }

    @Override
    public boolean toggleLike(Long memberId, LikeRequestDto dto) {
        QuestionBoard questionBoard = questionBoardRepository.findById(dto.getBoardId());
        if (existLike(memberId, questionBoard)) {
            removeLike(memberId, questionBoard);
            return false;
        } else {
            if (existDislike(memberId, questionBoard)) {
                removeDislike(memberId, questionBoard);
            }
            addLike(memberId, questionBoard);
            return true;
        }
    }

    private void addLike(Long memberId, QuestionBoard questionBoard) {
        Member member = em.getReference(Member.class, memberId);
        questionBoard.addThumbsUp(new ThumbsUp(member));
    }

    private void removeLike(Long memberId, QuestionBoard questionBoard) {
        questionBoard.getThumbsUps().removeIf(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    private boolean existLike(Long memberId, QuestionBoard questionBoard) {
        return questionBoard.getThumbsUps().stream()
                .anyMatch(thumbsUp -> thumbsUp.getMember().getId().equals(memberId));
    }

    @Override
    public boolean checkLike(Long memberId, Long boardId) {
        QuestionBoard questionBoard = questionBoardRepository.findById(boardId);
        return existLike(memberId, questionBoard);
    }

    @Override
    public boolean toggleDislike(Long memberId, LikeRequestDto dto) {
        QuestionBoard questionBoard = questionBoardRepository.findById(dto.getBoardId());
        if (existDislike(memberId, questionBoard)) {
            removeDislike(memberId, questionBoard);
            return false;
        } else {
            if (existLike(memberId, questionBoard)) {
                removeLike(memberId, questionBoard);
            }
            addDislike(memberId, questionBoard);
            return true;
        }
    }

    private void addDislike(Long memberId, QuestionBoard questionBoard) {
        Member member = em.getReference(Member.class, memberId);
        questionBoard.addThumbsDown(new ThumbsDown(member));
    }

    private void removeDislike(Long memberId, QuestionBoard questionBoard) {
        questionBoard.getThumbsDowns().removeIf(thumbsDown -> thumbsDown.getMember().getId().equals(memberId));
    }

    private boolean existDislike(Long memberId, QuestionBoard questionBoard) {
        return questionBoard.getThumbsDowns().stream()
                .anyMatch(thumbsDown -> thumbsDown.getMember().getId().equals(memberId));
    }

    @Override
    public boolean checkDislike(Long memberId, Long boardId) {
        QuestionBoard questionBoard = questionBoardRepository.findById(boardId);
        return existDislike(memberId, questionBoard);
    }

    @Override
    public void toggleRecruitment(Long memberId, BoardUpdateRequestDto dto) {
        return;
    }
}
