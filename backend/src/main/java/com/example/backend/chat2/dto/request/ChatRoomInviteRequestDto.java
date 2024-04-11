package com.example.backend.chat2.dto.request;

import lombok.Data;

@Data
public class ChatRoomInviteRequestDto {
    private String chatRoomId;
    private Long memberId;
}
