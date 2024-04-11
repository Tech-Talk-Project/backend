package com.example.backend.chat2.websocket;


import com.example.backend.chat2.repository.ChatMemberRepository;
import com.example.backend.chat2.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChatWebsocketEventListener {
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMemberRepository chatMemberRepository;

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
    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        if (chatSessionRepository.isSessionDataExist(sessionId)) {
            Map<String, String> sessionData = chatSessionRepository.getSessionData(sessionId);
            Long memberId = Long.parseLong(sessionData.get("memberId"));
            String chatRoomId = sessionData.get("chatRoomId");
            chatSessionRepository.deleteSessionData(sessionId);
            chatMemberRepository.updateLeaveTimeToNow(memberId, chatRoomId);
        }
    }
}
