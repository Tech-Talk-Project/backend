package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;

public interface BoardRepository {
    void save(BoardEntity boardEntity);
    BoardEntity findById(Long boardId);
    BoardEntity findByIdWithAll(Long boardId);
    void remove(BoardEntity boardEntity);
    void increaseViewCount(Long boardId);
}
