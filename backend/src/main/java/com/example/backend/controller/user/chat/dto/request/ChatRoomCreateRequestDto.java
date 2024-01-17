package com.example.backend.controller.user.chat.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomCreateRequestDto {
    private String title;
    private List<Long> memberIds;
}
