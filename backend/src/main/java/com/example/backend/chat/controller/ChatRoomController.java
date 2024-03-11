package com.example.backend.chat.controller;

import com.example.backend.chat.controller.dto.request.ChatRoomCreateRequestDto;
import com.example.backend.chat.controller.dto.request.LastMessageRequestDto;
import com.example.backend.chat.controller.dto.response.ChatRoomListResponseDto;
import com.example.backend.chat.controller.dto.response.ChatRoomResponseDto;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.service.ChatRoomSearchService;
import com.example.backend.chat.service.ChatRoomManageService;
import com.example.backend.controller.user.chat.dto.response.ChatRoomCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/chat/")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomManageService chatRoomManageService;
    private final ChatRoomSearchService chatRoomSearchService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoomCreateResponseDto> createRoom(@RequestBody ChatRoomCreateRequestDto requestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestDto.getMemberIds().add(memberId);
        ChatRoom chatRoom = chatRoomManageService.createChatRoom(requestDto.getTitle(), requestDto.getMemberIds());
        return ResponseEntity.ok(new ChatRoomCreateResponseDto(chatRoom));
    }

    @GetMapping("/rooms")
    public ResponseEntity<ChatRoomListResponseDto> getRooms() {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ChatRoomByMemberDto> chatRoomList = chatRoomSearchService.getChatRoomList(memberId);
        return ResponseEntity.ok(new ChatRoomListResponseDto(chatRoomList));
    }

    @PostMapping("/room")
    public ResponseEntity<ChatRoomResponseDto> getChatRoom(@RequestBody LastMessageRequestDto requestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ChatRoomResponseDto chatRoom = chatRoomSearchService.getChatRoom(memberId, requestDto.getChatRoomId());
        return ResponseEntity.ok(chatRoom);
    }
}
