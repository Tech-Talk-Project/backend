package com.example.backend.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QLike.like;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeRepository {
    private final JPAQueryFactory query;

    public void removeProjectLikeByMemberId(Long projectBoardId, Long memberId) {
        query.delete(like)
                .where(like.projectBoard.id.eq(projectBoardId)
                        .and(like.member.id.eq(memberId)))
                .execute();
    }
}
