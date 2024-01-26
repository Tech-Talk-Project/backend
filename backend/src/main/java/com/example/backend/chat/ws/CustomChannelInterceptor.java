package com.example.backend.chat.ws;

import com.example.backend.chat.service.ChatRoomValidator;
import com.example.backend.security.jwt.JwtClaimReader;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomChannelInterceptor implements ChannelInterceptor {
    private final JwtClaimReader jwtClaimReader;
    private final ChatRoomValidator chatRoomValidator;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String accessToken = accessor.getFirstNativeHeader("accessToken");
            Long memberId = jwtClaimReader.getMemberId(accessToken);
            String chatRoomId = accessor.getFirstNativeHeader("chatRoomId");

            if (!chatRoomValidator.isMemberOfChatRoom(chatRoomId, memberId)) {
                throw new IllegalArgumentException("채팅방에 참여하지 않은 사용자입니다.");
            }
        }

        return message;
    }
}
