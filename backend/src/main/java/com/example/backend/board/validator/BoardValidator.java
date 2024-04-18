package com.example.backend.board.validator;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.exception.AuthorForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardValidator {
    public void validateAuthor(Long memberId, BoardEntity board) {
        if (!board.getAuthor().getId().equals(memberId)) {
            throw new AuthorForbiddenException("작성자만 접근할 수 있습니다.");
        }
    }
}
