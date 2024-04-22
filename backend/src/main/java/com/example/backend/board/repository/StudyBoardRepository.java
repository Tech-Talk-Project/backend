package com.example.backend.board.repository;

import com.example.backend.board.domain.StudyBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QStudyBoard.studyBoard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public StudyBoard findById(Long studyBoardId) {
        return queryFactory
                .selectFrom(studyBoard)
                .where(studyBoard.id.eq(studyBoardId))
                .fetchOne();
    }

    @Transactional
    public void save(StudyBoard studyBoard) {
        em.persist(studyBoard);
    }

    @Transactional
    public void remove(StudyBoard studyBoard) {
        em.remove(studyBoard);
    }
}
