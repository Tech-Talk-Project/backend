package com.example.backend.chat.dto.request;

import lombok.Data;

@Data
public class ChatRoomTitleUpdateRequestDto {
    private String chatRoomId;
    private String title;
}
