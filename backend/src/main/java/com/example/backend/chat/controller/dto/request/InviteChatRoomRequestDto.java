package com.example.backend.chat.controller.dto.request;

import lombok.Data;

@Data
public class InviteChatRoomRequestDto {
    private String chatRoomId;
    private Long invitedMemberId;
}
