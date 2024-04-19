package com.example.backend.board.service;

import com.example.backend.board.domain.Comment;
import com.example.backend.board.dto.request.CommentUpdateRequestDto;
import com.example.backend.board.repository.CommentRepository;
import com.example.backend.board.validator.CommentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentManageService {
    private final CommentValidator commentValidator;
    private final CommentRepository commentRepository;

    public void updateComment(Long memberId, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId());
        commentValidator.validateAuthor(memberId, comment);
        comment.update(dto);
    }

    public void deleteComment(Long memberId, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId());
        commentValidator.validateAuthor(memberId, comment);
        commentRepository.remove(comment);
    }
}
