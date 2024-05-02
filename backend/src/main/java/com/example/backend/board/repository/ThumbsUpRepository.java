package com.example.backend.board.repository;

import com.example.backend.board.service.BoardCategory;
import com.example.backend.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QThumbsUp.thumbsUp;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThumbsUpRepository {
    private final JPAQueryFactory query;

    public void setNullMember(Long memberId) {
        query
                .update(thumbsUp)
                .set(thumbsUp.member, (Member) null)
                .where(thumbsUp.member.id.eq(memberId))
                .execute();
    }
}
