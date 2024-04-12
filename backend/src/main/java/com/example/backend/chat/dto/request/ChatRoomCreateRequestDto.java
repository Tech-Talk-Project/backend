package com.example.backend.chat.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomCreateRequestDto {
    private String title;
    private Long ownerId;
    List<Long> memberIds;
}
