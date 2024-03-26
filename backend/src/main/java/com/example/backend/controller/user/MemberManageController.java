package com.example.backend.controller.user;


import com.example.backend.auth.service.AuthService;
import com.example.backend.service.MemberRemoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberManageController {
    private final MemberRemoveService memberRemoveService;
    private final AuthService authService;

    @GetMapping("/removeMember")
    public ResponseEntity<String> removeMember(
            @RequestHeader(value = "Authorization", required = false) String accessTokenInHeader,
            @CookieValue(required = false) String refreshToken) {
        String accessToken = getAccessToken(accessTokenInHeader);
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberRemoveService.removeMember(memberId);
        authService.logout(accessToken, refreshToken);

        HttpCookie refreshTokenCookie = getRefreshTokenCookie("", 0L);
        return ResponseEntity
                .ok()
                .header("Set-Cookie", refreshTokenCookie.toString())
                .body("회원 탈퇴가 완료되었습니다.");
    }

    private String getAccessToken(String accessTokenInHeader) {
        String BEARER_PREFIX = "Bearer ";
        if (accessTokenInHeader.length() < BEARER_PREFIX.length())
            return "";
        return accessTokenInHeader.substring(BEARER_PREFIX.length());
    }

    private HttpCookie getRefreshTokenCookie(String refreshToken, long refreshTokenExpirationFromNowInSeconds) {
        HttpCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .maxAge(refreshTokenExpirationFromNowInSeconds)
//                .secure(true)
                .httpOnly(true)
                .build();
        return cookie;
    }
}
