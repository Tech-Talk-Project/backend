package com.example.backend.chat2.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomCreateRequestDto {
    private String title;
    private Long ownerId;
    List<Long> memberIds;
}
