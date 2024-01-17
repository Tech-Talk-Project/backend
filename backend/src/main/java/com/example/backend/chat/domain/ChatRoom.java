package com.example.backend.chat.domain;

import com.example.backend.chat.dto.ChatMessageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
public class ChatRoom {
    @Id
    private String id;
    private String title;
    private List<Long> joinedMemberIds = new ArrayList<>();

    // 100개까지만 저장하고 그 이전 메세지의 경우 ChatMessage 를 커서 기반으로 페이징합니다.
    private List<ChatMessageDto> lastMessages = new ArrayList<>();

    public ChatRoom(String title, List<Long> joinedMemberIds) {
        this.title = title;
        this.joinedMemberIds = joinedMemberIds;
    }
}
