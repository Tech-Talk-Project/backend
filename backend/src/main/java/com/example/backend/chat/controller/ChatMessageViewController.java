package com.example.backend.chat.controller;

import com.example.backend.chat.dto.response.ChatMessageListResponseDto;
import com.example.backend.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/chat/message")
public class ChatMessageViewController {
    private final ChatMessageService chatMessageService;

    @GetMapping("/cursor")
    public ResponseEntity<ChatMessageListResponseDto> getMessagesBeforeCursor(
            @RequestParam String chatRoomId, @RequestParam LocalDateTime cursor
    ) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(chatMessageService.getMessagesBeforeCursor(memberId, chatRoomId, cursor));
    }
}
