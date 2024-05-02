package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.domain.StudyBoard;
import com.example.backend.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.board.domain.QStudyBoard.studyBoard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardRepository implements BoardRepository{
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Override
    public StudyBoard findById(Long studyBoardId) {
        StudyBoard result = query
                .selectFrom(studyBoard)
                .where(studyBoard.id.eq(studyBoardId))
                .fetchOne();
        if (result == null) {
            throw new IllegalArgumentException("없는 게시물입니다.: " + studyBoardId);
        }
        return result;
    }

    @Override
    public StudyBoard findByIdWithAll(Long studyBoardId) {
        StudyBoard result = query
                .selectFrom(studyBoard)
                .leftJoin(studyBoard.author).fetchJoin()
                .where(studyBoard.id.eq(studyBoardId))
                .fetchOne();
        if (result == null) {
            throw new IllegalArgumentException("없는 게시물입니다.: " + studyBoardId);
        }
        return result;
    }

    @Transactional
    @Override
    public void save(BoardEntity studyBoard) {
        em.persist(studyBoard);
    }

    @Transactional
    @Override
    public void remove(BoardEntity studyBoard) {
        em.remove(studyBoard);
    }

    @Transactional
    @Override
    public void increaseViewCount(Long boardId) {
        query
                .update(studyBoard)
                .where(studyBoard.id.eq(boardId))
                .set(studyBoard.viewCount, studyBoard.viewCount.add(1))
                .execute();
    }

    @Override
    public Long countAll() {
        return query
                .selectFrom(studyBoard)
                .fetchCount();
    }

    public List<StudyBoard> findAll(int page, int size) {
        return query
                .selectFrom(studyBoard)
                .orderBy(studyBoard.id.desc())
                .offset((long) page * size)
                .limit(size)
                .fetch();
    }

    public void setNullMember(Long memberId) {
        query
                .update(studyBoard)
                .where(studyBoard.author.id.eq(memberId))
                .set(studyBoard.author, (Member) null)
                .execute();
    }
}
