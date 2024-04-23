package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.domain.FreeBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QFreeBoard.freeBoard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreeBoardRepository implements BoardRepository{
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Override
    @Transactional
    public void save(BoardEntity boardEntity) {
        em.persist(boardEntity);
    }

    @Override
    public FreeBoard findById(Long boardId) {
        return query
                .selectFrom(freeBoard)
                .where(freeBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public FreeBoard findByIdWithAll(Long boardId) {
        return query
                .selectFrom(freeBoard)
                .leftJoin(freeBoard.author).fetchJoin()
                .where(freeBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    @Transactional
    public void remove(BoardEntity boardEntity) {
        em.remove(boardEntity);
    }

    @Override
    @Transactional
    public void increaseViewCount(Long boardId) {
        query
                .update(freeBoard)
                .where(freeBoard.id.eq(boardId))
                .set(freeBoard.viewCount, freeBoard.viewCount.add(1))
                .execute();
    }
}
