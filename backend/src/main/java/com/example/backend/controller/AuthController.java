package com.example.backend.controller;

import com.example.backend.auth.dto.LoginSuccessDto;
import com.example.backend.auth.dto.request.LoginRequestDto;
import com.example.backend.auth.dto.response.LoginResponseDto;
import com.example.backend.auth.dto.response.RefreshResponseDto;
import com.example.backend.auth.service.AuthService;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.security.dto.AtRtDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String code = loginRequestDto.getCode();
        OAuth2Provider oAuth2Provider = OAuth2Provider.valueOf(loginRequestDto.getProvider().toUpperCase());
        LoginSuccessDto loginSuccessDto = authService.login(code, oAuth2Provider);

        String refreshToken = loginSuccessDto.getAtRtDto().getRefreshToken();
        long refreshTokenExpirationFromNowInSeconds =
                loginSuccessDto.getAtRtDto().getRefreshTokenExpirationInMilliseconds() / 1000L;
        HttpCookie cookie = getHttpCookie(refreshToken, refreshTokenExpirationFromNowInSeconds);

        return ResponseEntity
                .ok()
                .header("Set-Cookie", cookie.toString())
                .body(new LoginResponseDto(loginSuccessDto));
    }

    @GetMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(@CookieValue String refreshToken) {
        AtRtDto atRtDto = authService.refresh(refreshToken);
        String newRefreshToken = atRtDto.getRefreshToken();
        long newRefreshTokenExpirationFromNowInSeconds =
                atRtDto.getRefreshTokenExpirationInMilliseconds() / 1000L;
        HttpCookie cookie = getHttpCookie(newRefreshToken, newRefreshTokenExpirationFromNowInSeconds);

        return ResponseEntity
                .ok()
                .header("Set-Cookie", cookie.toString())
                .body(new RefreshResponseDto(atRtDto));
    }

    private HttpCookie getHttpCookie(String refreshToken, long refreshTokenExpirationFromNowInSeconds) {
        HttpCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .maxAge(refreshTokenExpirationFromNowInSeconds)
//                .secure(true)
                .httpOnly(true)
                .build();
        return cookie;
    }
}
