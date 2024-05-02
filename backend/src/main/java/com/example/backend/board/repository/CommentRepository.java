package com.example.backend.board.repository;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.dto.request.CommentUpdateRequestDto;
import com.example.backend.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QComment.comment;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentRepository {
    private final JPAQueryFactory query;
    private final EntityManager em;

    public Comment findById(Long commentId) {
        return query
                .selectFrom(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();
    }

    @Transactional
    public void remove(Comment comment) {
        em.remove(comment);
    }

    @Transactional
    public void setNullMember(Long memberId) {
        query
                .update(comment)
                .set(comment.author, (Member) null)
                .where(comment.author.id.eq(memberId))
                .execute();
    }
}
