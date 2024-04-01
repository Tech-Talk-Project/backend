package com.example.backend.chat.controller;

import com.example.backend.chat.controller.dto.request.ChatRoomCreateRequestDto;
import com.example.backend.chat.controller.dto.request.LastMessageRequestDto;
import com.example.backend.chat.controller.dto.request.TitleChangeRequestDto;
import com.example.backend.chat.controller.dto.response.ChatRoomListResponseDto;
import com.example.backend.chat.controller.dto.response.ChatRoomResponseDto;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.service.ChatMemberService;
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
    private final ChatMemberService chatMemberService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoomCreateResponseDto> createRoom(@RequestBody ChatRoomCreateRequestDto requestDto) {
        Long roomOwnerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ChatRoom chatRoom = chatRoomManageService.createChatRoom(
                requestDto.getTitle(),roomOwnerId, requestDto.getMemberIds());
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

    @GetMapping("/leave")
    public ResponseEntity<String> leaveChatRoom(@RequestParam String chatRoomId) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chatMemberService.leaveChatRoom(memberId, chatRoomId);
        return ResponseEntity.ok("채팅방을 종료했습니다.");
    }

    @GetMapping("/exit")
    public ResponseEntity<String> exitChatRoom(@RequestParam String chatRoomId) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chatMemberService.exitChatRoom(chatRoomId, memberId);
        return ResponseEntity.ok("채팅방을 나갔습니다.");
    }

    @PostMapping("/change-title")
    public ResponseEntity<String> changeTitle(@RequestBody TitleChangeRequestDto dto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chatRoomManageService.changeTitle(memberId, dto.getChatRoomId(), dto.getNewTitle());
        return ResponseEntity.ok("채팅방 제목을 변경했습니다.");
    }
}
