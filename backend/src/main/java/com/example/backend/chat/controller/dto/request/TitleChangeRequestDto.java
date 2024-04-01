package com.example.backend.chat.controller.dto.request;

import lombok.Data;

@Data
public class TitleChangeRequestDto {
    String chatRoomId;
    String newTitle;
}
