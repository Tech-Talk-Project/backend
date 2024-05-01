package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.domain.ProjectBoard;
import com.example.backend.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.board.domain.QProjectBoard.projectBoard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectBoardRepository implements BoardRepository {
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Transactional
    @Override
    public void save(BoardEntity projectBoard) {
        em.persist(projectBoard);
    }

    @Override
    public ProjectBoard findById(Long projectBoardId) {
        ProjectBoard result = query
                .selectFrom(projectBoard)
                .where(projectBoard.id.eq(projectBoardId))
                .fetchOne();
        if (result == null) {
            throw new IllegalArgumentException("없는 게시물입니다.: " + projectBoardId);
        }
        return result;
    }


    @Override
    public ProjectBoard findByIdWithAll(Long projectBoardId) {
        ProjectBoard result = query
                .selectFrom(projectBoard)
                .leftJoin(projectBoard.author).fetchJoin()
                .where(projectBoard.id.eq(projectBoardId))
                .fetchOne();
        if (result == null) {
            throw new IllegalArgumentException("없는 게시물입니다.: " + projectBoardId);
        }
        return result;
    }

    @Transactional
    @Override
    public void remove(BoardEntity projectBoard) {
        em.remove(projectBoard);
    }

    @Transactional
    @Override
    public void increaseViewCount(Long projectBoardId) {
        query
                .update(projectBoard)
                .where(projectBoard.id.eq(projectBoardId))
                .set(projectBoard.viewCount, projectBoard.viewCount.add(1))
                .execute();
    }

    @Override
    public Long countAll() {
        return query
                .selectFrom(projectBoard)
                .fetchCount();
    }

    public List<ProjectBoard> findAll(int page, int size) {
        return query
                .selectFrom(projectBoard)
                .orderBy(projectBoard.createdAt.desc())
                .offset((long) page * size)
                .limit(size)
                .fetch();
    }

    public void setNullMember(Long memberId) {
        query
                .update(projectBoard)
                .set(projectBoard.author, (Member) null)
                .where(projectBoard.author.id.eq(memberId))
                .execute();
    }
}
