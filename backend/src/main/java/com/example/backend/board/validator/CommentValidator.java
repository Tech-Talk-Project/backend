package com.example.backend.board.validator;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.exception.AuthorForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {
    public void validateAuthor(Long memberId, Comment comment) {
        if (!comment.getAuthor().getId().equals(memberId)) {
            throw new AuthorForbiddenException("작성자만 접근할 수 있습니다.");
        }
    }
}
