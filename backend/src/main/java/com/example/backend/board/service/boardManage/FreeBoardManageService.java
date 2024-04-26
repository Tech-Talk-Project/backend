package com.example.backend.board.service.boardManage;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.domain.FreeBoard;
import com.example.backend.board.domain.ThumbsDown;
import com.example.backend.board.domain.ThumbsUp;
import com.example.backend.board.dto.request.*;
import com.example.backend.board.repository.FreeBoardRepository;
import com.example.backend.board.validator.BoardValidator;
import com.example.backend.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FreeBoardManageService implements BoardManageService{
    private final FreeBoardRepository freeBoardRepository;
    private final BoardValidator boardValidator;
    private final EntityManager em;

    @Override
    public Long create(Long memberId, BoardCreateRequestDto dto) {
        Member member = em.getReference(Member.class, memberId);
        FreeBoard freeBoard = new FreeBoard(member, dto);
        freeBoardRepository.save(freeBoard);
        return freeBoard.getId();
    }

    @Override
    public void update(Long memberId, BoardUpdateRequestDto dto) {
        FreeBoard freeBoard = freeBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, freeBoard);
        freeBoard.update(dto);
    }

    @Override
    public void delete(Long memberId, BoardDeleteRequestDto dto) {
        FreeBoard freeBoard = freeBoardRepository.findById(dto.getBoardId());
        boardValidator.validateAuthor(memberId, freeBoard);
        freeBoardRepository.remove(freeBoard);
    }

    @Override
    public void addComment(Long memberId, CommentAddRequestDto dto) {
        FreeBoard freeBoard = freeBoardRepository.findById(dto.getBoardId());
        Member member = em.getReference(Member.class, memberId);
        freeBoard.addComment(new Comment(dto.getContent(), member));
    }

    @Override
    public boolean toggleLike(Long memberId, LikeRequestDto dto) {
        FreeBoard freeBoard = freeBoardRepository.findById(dto.getBoardId());
        if (existLike(memberId, freeBoard)) {
            removeLike(memberId, freeBoard);
            return false;
        } else {
            if (existDislike(memberId, freeBoard)) {
                removeDislike(memberId, freeBoard);
            }
            addLike(memberId, freeBoard);
            return true;
        }
    }

    private void addLike(Long memberId, FreeBoard freeBoard) {
        Member member = em.getReference(Member.class, memberId);
        freeBoard.getThumbsUps().add(new ThumbsUp(member));
    }

    private void removeLike(Long memberId, FreeBoard freeBoard) {
        freeBoard.getThumbsUps().removeIf(like -> like.getMember().getId().equals(memberId));
    }

    private boolean existLike(Long memberId, FreeBoard freeBoard) {
        return freeBoard.getThumbsUps().stream()
                .anyMatch(like -> like.getMember().getId().equals(memberId));
    }

    @Override
    public boolean checkLike(Long memberId, Long boardId) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardId);
        return existLike(memberId, freeBoard);
    }

    @Override
    public boolean toggleDislike(Long memberId, LikeRequestDto dto) {
        FreeBoard freeBoard = freeBoardRepository.findById(dto.getBoardId());
        if (existDislike(memberId, freeBoard)) {
            removeDislike(memberId, freeBoard);
            return false;
        } else {
            if (existLike(memberId, freeBoard)) {
                removeLike(memberId, freeBoard);
            }
            addDislike(memberId, freeBoard);
            return true;
        }
    }

    private void addDislike(Long memberId, FreeBoard freeBoard) {
        Member member = em.getReference(Member.class, memberId);
        freeBoard.getThumbsDowns().add(new ThumbsDown(member));
    }

    private void removeDislike(Long memberId, FreeBoard freeBoard) {
        freeBoard.getThumbsDowns().removeIf(dislike -> dislike.getMember().getId().equals(memberId));
    }

    private boolean existDislike(Long memberId, FreeBoard freeBoard) {
        return freeBoard.getThumbsDowns().stream()
                .anyMatch(dislike -> dislike.getMember().getId().equals(memberId));
    }

    @Override
    public boolean checkDislike(Long memberId, Long boardId) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardId);
        return existDislike(memberId, freeBoard);
    }
}
