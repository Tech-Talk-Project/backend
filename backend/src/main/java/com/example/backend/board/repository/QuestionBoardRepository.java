package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.domain.QuestionBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.board.domain.QQuestionBoard.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionBoardRepository implements BoardRepository{
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Override
    public void save(BoardEntity boardEntity) {
        em.persist(boardEntity);
    }

    @Override
    public QuestionBoard findById(Long boardId) {
        return query
                .selectFrom(questionBoard)
                .where(questionBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public QuestionBoard findByIdWithAll(Long boardId) {
        return query
                .selectFrom(questionBoard)
                .join(questionBoard.author).fetchJoin()
                .where(questionBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public void remove(BoardEntity boardEntity) {
        em.remove(boardEntity);
    }

    @Override
    public void increaseViewCount(Long boardId) {
        query
                .update(questionBoard)
                .where(questionBoard.id.eq(boardId))
                .set(questionBoard.viewCount, questionBoard.viewCount.add(1))
                .execute();
    }

    public List<QuestionBoard> findAll(int page, int size) {
        return query
                .selectFrom(questionBoard)
                .orderBy(questionBoard.createdAt.desc())
                .offset((long) page * size)
                .limit(size)
                .fetch();
    }
}
