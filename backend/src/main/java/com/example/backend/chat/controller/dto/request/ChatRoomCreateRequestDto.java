package com.example.backend.chat.controller.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomCreateRequestDto {
    private String title;
    private List<Long> memberIds;
}
