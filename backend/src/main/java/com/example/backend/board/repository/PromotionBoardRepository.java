package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.domain.PromotionBoard;
import com.example.backend.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        PromotionBoard result = query
                .selectFrom(promotionBoard)
                .where(promotionBoard.id.eq(boardId))
                .fetchOne();
        if (result == null) {
            throw new IllegalArgumentException("없는 게시물입니다.: " + boardId);
        }
        return result;
    }


    @Override
    public PromotionBoard findByIdWithAll(Long boardId) {
        PromotionBoard result = query
                .selectFrom(promotionBoard)
                .leftJoin(promotionBoard.author).fetchJoin()
                .where(promotionBoard.id.eq(boardId))
                .fetchOne();
        if (result == null) {
            throw new IllegalArgumentException("없는 게시물입니다.: " + boardId);
        }
        return result;
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

    @Override
    public Long countAll() {
        return query
                .selectFrom(promotionBoard)
                .fetchCount();
    }

    public List<PromotionBoard> findAll(int page, int size) {
        return query
                .selectFrom(promotionBoard)
                .orderBy(promotionBoard.id.desc())
                .offset((long) page * size)
                .limit(size)
                .fetch();
    }

    public void setNullMember(Long member) {
        query
                .update(promotionBoard)
                .set(promotionBoard.author, (Member) null)
                .where(promotionBoard.author.id.eq(member))
                .execute();
    }
}
