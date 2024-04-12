package com.example.backend.chat2.dto.response;

import com.example.backend.chat2.dto.ChatRoomInfoWhenListDto;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomListViewDto {
    private List<ChatRoomInfoWhenListDto> chatRoomList;

    public ChatRoomListViewDto(List<ChatRoomInfoWhenListDto> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }
}
