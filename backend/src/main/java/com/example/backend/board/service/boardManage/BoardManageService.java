package com.example.backend.board.service.boardManage;

import com.example.backend.board.dto.request.*;

public interface BoardManageService {
    Long create(Long memberId, BoardCreateRequestDto dto);

    void update(Long memberId, BoardUpdateRequestDto dto);

    void delete(Long memberId, BoardDeleteRequestDto dto);

    void addComment(Long memberId, CommentAddRequestDto dto);

    /**
     * 좋아요 토글 <br>
     * 이미 좋아요가 눌려있으면 좋아요 취소 후 false 반환, 아니면 좋아요 추가 후 true 반환
     * @param memberId
     * @param dto
     * @return
     */
    boolean toggleLike(Long memberId, LikeRequestDto dto);

    boolean checkLike(Long memberId, Long boardId);

    boolean toggleDislike(Long memberId, LikeRequestDto dto);

    boolean checkDislike(Long memberId, Long boardId);

    void toggleRecruitment(Long memberId, BoardUpdateRequestDto dto);
}
