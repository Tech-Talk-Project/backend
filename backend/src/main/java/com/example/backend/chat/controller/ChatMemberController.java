package com.example.backend.chat.controller;

import com.example.backend.chat.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/chat")
@RequiredArgsConstructor
public class ChatMemberController {
    private final ChatMemberService chatMemberService;

    @GetMapping("/out")
    public ResponseEntity<String> outChatRoom(
            @RequestParam String chatRoomId
    ) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chatMemberService.outChatRoom(memberId, chatRoomId);
        return ResponseEntity.ok("채팅방을 종료했습니다.");
    }
}
