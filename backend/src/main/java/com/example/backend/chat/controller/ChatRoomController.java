package com.example.backend.chat.controller;

import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.service.ChatRoomListService;
import com.example.backend.chat.service.ChatRoomManageService;
import com.example.backend.controller.user.chat.dto.request.ChatRoomCreateRequestDto;
import com.example.backend.controller.user.chat.dto.response.ChatRoomCreateResponseDto;
import com.example.backend.controller.user.chat.dto.response.ChatRoomListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomManageService chatRoomManageService;
    private final ChatRoomListService chatRoomListService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoomCreateResponseDto> createRoom(@RequestBody ChatRoomCreateRequestDto requestDto) {
        ChatRoom chatRoom = chatRoomManageService.createChatRoom(requestDto.getTitle(), requestDto.getMemberIds());
        return ResponseEntity.ok(new ChatRoomCreateResponseDto(chatRoom));
    }

    @GetMapping("/rooms")
    public ResponseEntity<ChatRoomListResponseDto> getRooms() {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ChatRoomByMemberDto> chatRoomList = chatRoomListService.getChatRoomList(memberId);
        return ResponseEntity.ok(new ChatRoomListResponseDto(chatRoomList));
    }
}
