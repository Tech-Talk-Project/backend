package com.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = headerAccessor.getFirstNativeHeader("userId");
        String chatRoomId = headerAccessor.getFirstNativeHeader("chatRoomId");

        System.out.println("connected " + userId + "  " + chatRoomId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = headerAccessor.getFirstNativeHeader("userId");
        String chatRoomId = headerAccessor.getFirstNativeHeader("chatRoomId");

        System.out.println("disconnected " + userId + "  " + chatRoomId);
    }
}
