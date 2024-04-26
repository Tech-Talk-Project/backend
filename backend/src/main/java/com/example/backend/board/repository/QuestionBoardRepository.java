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
        QuestionBoard result = query
                .selectFrom(questionBoard)
                .where(questionBoard.id.eq(boardId))
                .fetchOne();
        if (result == null) {
            throw new IllegalArgumentException("없는 게시물입니다.: " + boardId);
        }
        return result;
    }

    @Override
    public QuestionBoard findByIdWithAll(Long boardId) {
        QuestionBoard result = query
                .selectFrom(questionBoard)
                .join(questionBoard.author).fetchJoin()
                .where(questionBoard.id.eq(boardId))
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
                .update(questionBoard)
                .where(questionBoard.id.eq(boardId))
                .set(questionBoard.viewCount, questionBoard.viewCount.add(1))
                .execute();
    }

    @Override
    public Long countAll() {
        return query
                .selectFrom(questionBoard)
                .fetchCount();
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
