package com.example.backend.auth.dto.response;

import com.example.backend.security.dto.AtRtDto;
import lombok.Data;

@Data
public class RefreshResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationInMilliSeconds;

    public RefreshResponseDto(AtRtDto atRtDto) {
        this.accessToken = atRtDto.getAccessToken();
        this.refreshToken = atRtDto.getRefreshToken();
        this.refreshTokenExpirationInMilliSeconds = atRtDto.getRefreshTokenExpirationInMilliseconds();
    }
}
