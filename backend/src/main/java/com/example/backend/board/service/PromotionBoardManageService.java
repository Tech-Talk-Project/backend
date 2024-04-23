package com.example.backend.board.service;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.domain.PromotionBoard;
import com.example.backend.board.domain.ThumbsDown;
import com.example.backend.board.domain.ThumbsUp;
import com.example.backend.board.dto.request.*;
import com.example.backend.board.repository.PromotionBoardRepository;
import com.example.backend.board.validator.BoardValidator;
import com.example.backend.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionBoardManageService implements BoardManageService{
    private final PromotionBoardRepository promotionBoardRepository;
    private final EntityManager em;
    private final BoardValidator boardValidator;

    @Override
    public Long create(Long memberId, BoardCreateRequestDto dto) {
        Member member = em.getReference(Member.class, memberId);
        PromotionBoard questionBoard = new PromotionBoard(member, dto);
        promotionBoardRepository.save(questionBoard);
        return questionBoard.getId();
    }

    @Override
    public void update(Long memberId, BoardUpdateRequestDto dto) {
        PromotionBoard promotionBoard = promotionBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, promotionBoard);
        promotionBoard.update(dto);
    }

    @Override
    public void delete(Long memberId, BoardDeleteRequestDto dto) {
        PromotionBoard promotionBoard = promotionBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, promotionBoard);
        promotionBoardRepository.remove(promotionBoard);
    }

    @Override
    public void addComment(Long memberId, CommentAddRequestDto dto) {
        PromotionBoard promotionBoard = promotionBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        promotionBoard.addComment(new Comment(dto.getContent(), member));
    }

    @Override
    public boolean toggleLike(Long memberId, LikeRequestDto dto) {
        PromotionBoard promotionBoard = promotionBoardRepository.findById(dto.getBoardId());
        if (existLike(memberId, promotionBoard)) {
            removeLike(memberId, promotionBoard);
            return false;
        } else {
            addLike(memberId, promotionBoard);
            return true;
        }
    }

    private void addLike(Long memberId, PromotionBoard promotionBoard) {
        Member member = em.getReference(Member.class, memberId);
        promotionBoard.addThumbsUp(new ThumbsUp(member));
    }

    private void removeLike(Long memberId, PromotionBoard promotionBoard) {
        promotionBoard.getThumbsUps().removeIf(like -> like.getMember().getId().equals(memberId));
    }

    private boolean existLike(Long memberId, PromotionBoard promotionBoard) {
        return promotionBoard.getThumbsUps().stream()
                .anyMatch(like -> like.getMember().getId().equals(memberId));
    }

    @Override
    public boolean checkLike(Long memberId, Long boardId) {
        PromotionBoard promotionBoard = promotionBoardRepository.findById(boardId);
        return existLike(memberId, promotionBoard);
    }

    @Override
    public boolean toggleDislike(Long memberId, LikeRequestDto dto) {
        PromotionBoard promotionBoard = promotionBoardRepository.findById(dto.getBoardId());
        if (existDisLike(memberId, promotionBoard)) {
            removeDisLike(memberId, promotionBoard);
            return false;
        } else {
            addDisLike(memberId, promotionBoard);
            return true;
        }
    }

    private void addDisLike(Long memberId, PromotionBoard promotionBoard) {
        Member member = em.getReference(Member.class, memberId);
        promotionBoard.addThumbsDown(new ThumbsDown(member));
    }

    private void removeDisLike(Long memberId, PromotionBoard promotionBoard) {
        promotionBoard.getThumbsDowns().removeIf(dislike -> dislike.getMember().getId().equals(memberId));
    }

    private boolean existDisLike(Long memberId, PromotionBoard promotionBoard) {
        return promotionBoard.getThumbsDowns().stream()
                .anyMatch(dislike -> dislike.getMember().getId().equals(memberId));
    }

    @Override
    public boolean checkDislike(Long memberId, Long boardId) {
        PromotionBoard promotionBoard = promotionBoardRepository.findById(boardId);
        return existDisLike(memberId, promotionBoard);
    }
}
