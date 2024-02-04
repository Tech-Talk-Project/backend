package com.example.backend.chat.controller;

import com.example.backend.chat.repository.ChatSessionRepository;
import com.example.backend.chat.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMemberService chatMemberService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String type = headerAccessor.getFirstNativeHeader("type");
        if (WebSocketConnectionType.valueOf(type) == WebSocketConnectionType.CHAT_ROOM) {
            String sessionId = headerAccessor.getSessionId();
            String memberId = headerAccessor.getFirstNativeHeader("memberId");
            String chatRoomId = headerAccessor.getFirstNativeHeader("chatRoomId");
            chatSessionRepository.saveSessionData(sessionId, memberId, chatRoomId);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        Map<String, String> sessionData = chatSessionRepository.getSessionData(sessionId);

        if (chatSessionRepository.isSessionDataExist(sessionId)) {
            Long memberId = Long.parseLong(sessionData.get("memberId"));
            String chatRoomId = sessionData.get("chatRoomId");
            chatMemberService.leaveChatRoom(memberId, chatRoomId);
            chatSessionRepository.deleteSessionData(sessionId);
        }
    }
}