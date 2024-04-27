package com.example.backend.board.service.boardView;

import com.example.backend.board.domain.FreeBoard;
import com.example.backend.board.dto.SimpleBoardDto;
import com.example.backend.board.dto.response.BoardPageResponseDto;
import com.example.backend.board.dto.response.BoardViewResponseDto;
import com.example.backend.board.repository.FreeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreeBoardViewService implements BoardViewService{
    private final FreeBoardRepository freeBoardRepository;

    @Override
    @Transactional
    public BoardViewResponseDto getBoard(Long boardId) {
        FreeBoard freeBoard = freeBoardRepository.findByIdWithAll(boardId);
        freeBoard.increaseViewCount();
        return new BoardViewResponseDto(freeBoard);
    }

    @Override
    public BoardPageResponseDto getBoards(int page, int size) {
        List<FreeBoard> freeBoards = freeBoardRepository.findAll(page, size);
        Long totalCount = freeBoardRepository.countAll();
        int totalPage = (int) Math.ceil((double) totalCount / size);
        List<SimpleBoardDto> simpleBoards = SimpleBoardDto.ofFreeBoards(freeBoards);
        return new BoardPageResponseDto(simpleBoards, totalPage);
    }
}
