package com.example.backend.board.service;

import com.example.backend.board.dto.request.BoardCreateRequestDto;
import com.example.backend.board.dto.request.BoardUpdateRequestDto;
import com.example.backend.board.dto.request.CommentAddRequestDto;
import com.example.backend.board.dto.request.LikeRequestDto;

public interface BoardManageService {
    Long createBoard(Long memberId, BoardCreateRequestDto dto);

    void updateBoard(Long memberId, BoardUpdateRequestDto dto);

    void deleteBoard(Long memberId, Long boardId);

    void addComment(Long memberId, CommentAddRequestDto dto);

    boolean toggleLike(Long memberId, LikeRequestDto dto);

    boolean checkLike(Long memberId, Long boardId);

    boolean toggleDislike(Long memberId, LikeRequestDto dto);

    boolean checkDislike(Long memberId, Long boardId);
}
