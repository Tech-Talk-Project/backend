package com.example.backend.security.repository;

import com.example.backend.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    @Value("${secret.refresh-token-validity-in-seconds}")
    private Long refreshTokenValidityInSeconds;
    private final RedisRepository redisRepository;
    private String getKey(String token) {
        return "refresh_token:" + token;
    }
    public void mapRtToAt(String refreshToken, String accessToken) {
        redisRepository.setWithTimeout(getKey(refreshToken), accessToken,
                refreshTokenValidityInSeconds);
    }
    public String getAt(String refreshToken) {
        // 서비스 안에서 rt 는 만료될 수 있기 때문에 에러를 던지지 않고 null 을 반환합니다.
        return redisRepository.get(getKey(refreshToken)).orElse(null);
    }
    public void delete(String refreshToken) {
        redisRepository.delete(getKey(refreshToken));
    }

    public boolean isExist(String refreshToken) {
        return redisRepository.get(getKey(refreshToken)).isPresent();
    }
}
