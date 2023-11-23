package com.example.backend.security.service;

import com.example.backend.security.dto.AtRtDto;
import com.example.backend.security.jwt.JwtClaimReader;
import com.example.backend.security.jwt.JwtCreator;
import com.example.backend.security.repository.AccessTokenRepository;
import com.example.backend.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtRtCreateService {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtCreator jwtCreator;
    private final JwtClaimReader jwtClaimReader;

    public AtRtDto create(Long memberId) {
        String accessToken = jwtCreator.createAccessToken(memberId);
        String refreshToken = jwtCreator.createRefreshToken(memberId);
        Long accessTokenExpirationInMilliseconds = jwtClaimReader.getExpirationInMilliseconds(accessToken);
        Long refreshTokenExpirationInMilliseconds = jwtClaimReader.getExpirationInMilliseconds(refreshToken);

        accessTokenRepository.mapAtToRt(accessToken, refreshToken);
        refreshTokenRepository.mapRtToAt(refreshToken, accessToken);

        return new AtRtDto(accessToken, refreshToken,
                accessTokenExpirationInMilliseconds, refreshTokenExpirationInMilliseconds);
    }

    public AtRtDto refresh(String refreshToken) {
        Long memberId = jwtClaimReader.getMemberId(refreshToken);

        String oldAccessToken = refreshTokenRepository.getAt(refreshToken);
        accessTokenRepository.delete(oldAccessToken);
        refreshTokenRepository.delete(refreshToken);

        return create(memberId);
    }
}
