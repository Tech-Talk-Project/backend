package com.example.backend.board.repository;

import com.example.backend.board.service.BoardCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QThumbsDown.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThumbsDownRepository {
    private final JPAQueryFactory query;
}
