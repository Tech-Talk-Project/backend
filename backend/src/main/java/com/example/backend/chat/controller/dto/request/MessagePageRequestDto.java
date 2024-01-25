package com.example.backend.chat.controller.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class MessagePageRequestDto {
    private String chatRoomId;
    private Date cursor;
}
