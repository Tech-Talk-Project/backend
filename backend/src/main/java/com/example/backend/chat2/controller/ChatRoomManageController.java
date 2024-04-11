package com.example.backend.chat2.controller;

import com.example.backend.chat2.dto.request.ChatRoomCreateRequestDto;
import com.example.backend.chat2.dto.request.ChatRoomInviteRequestDto;
import com.example.backend.chat2.dto.request.ChatRoomLeaveRequestDto;
import com.example.backend.chat2.dto.request.ChatRoomTitleUpdateRequestDto;
import com.example.backend.chat2.dto.response.ChatRoomCreateResponseDto;
import com.example.backend.chat2.service.ChatRoomManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/chat")
@RequiredArgsConstructor
public class ChatRoomManageController {
    private final ChatRoomManageService chatRoomManageService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoomCreateResponseDto> createChatRoom(
            @RequestBody ChatRoomCreateRequestDto requestDto) {
        String chatRoomId = chatRoomManageService.createChatRoom(requestDto);
        return ResponseEntity.ok(new ChatRoomCreateResponseDto(chatRoomId));
    }

    @PostMapping("/invite")
    public ResponseEntity<String> inviteChatRoom(
            @RequestBody ChatRoomInviteRequestDto requestDto) {
        chatRoomManageService.inviteMember(requestDto);
        return ResponseEntity.ok("초대를 완료했습니다.");
    }

    @PostMapping("/leave")
    public ResponseEntity<String> leaveChatRoom(
            @RequestBody ChatRoomLeaveRequestDto requestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chatRoomManageService.leaveChatRoom(memberId, requestDto);
        return ResponseEntity.ok("채팅방을 나갔습니다.");
    }

    @PostMapping("/change-title")
    public ResponseEntity<String> titleUpdate(
            @RequestBody ChatRoomTitleUpdateRequestDto requestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chatRoomManageService.changeTitle(memberId,  requestDto);
        return ResponseEntity.ok("채팅방 제목을 변경했습니다.");
    }

}
