package com.example.backend.chat.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDto {
    private Long senderId;
    private Date sendTime;
    private String content;
}
