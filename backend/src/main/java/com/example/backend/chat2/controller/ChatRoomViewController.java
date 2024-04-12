package com.example.backend.chat2.controller;

import com.example.backend.chat2.dto.response.ChatRoomViewResponseDto;
import com.example.backend.chat2.service.ChatRoomViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/chat")
public class ChatRoomViewController {
    private final ChatRoomViewService chatRoomViewService;

    @GetMapping("/room")
    public ResponseEntity<ChatRoomViewResponseDto> viewChatRoom(
            @RequestParam String chatRoomId
    ) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(chatRoomViewService.viewChatRoom(memberId, chatRoomId));
    }
}
