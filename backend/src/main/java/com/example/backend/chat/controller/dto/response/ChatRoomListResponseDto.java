package com.example.backend.controller.user.chat.dto.response;

import com.example.backend.chat.dto.ChatRoomByMemberDto;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomListResponseDto {
    private List<ChatRoomByMemberDto> chatRoomList;

    public ChatRoomListResponseDto(List<ChatRoomByMemberDto> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }
}
