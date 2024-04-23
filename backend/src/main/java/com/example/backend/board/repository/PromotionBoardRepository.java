package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.domain.PromotionBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QPromotionBoard.promotionBoard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionBoardRepository implements BoardRepository {
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Override
    public void save(BoardEntity boardEntity) {
        em.persist(boardEntity);
    }

    @Override
    public PromotionBoard findById(Long boardId) {
        return query
                .selectFrom(promotionBoard)
                .where(promotionBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public PromotionBoard findByIdWithAll(Long boardId) {
        return query
                .selectFrom(promotionBoard)
                .join(promotionBoard.author).fetchJoin()
                .where(promotionBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public void remove(BoardEntity boardEntity) {
        em.remove(boardEntity);
    }

    @Override
    public void increaseViewCount(Long boardId) {
        query
                .update(promotionBoard)
                .where(promotionBoard.id.eq(boardId))
                .set(promotionBoard.viewCount, promotionBoard.viewCount.add(1))
                .execute();
    }
}
