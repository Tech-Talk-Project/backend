package com.example.backend.security.repository;

import com.example.backend.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class AccessTokenRepository {
    @Value("${secret.access-token-validity-in-seconds}")
    private Long accessTokenValidityInSeconds;
    private final RedisRepository redisRepository;

    private String getKey(String token) {
        return "access_token:" + token;
    }

    public void mapAtToRt(String accessToken, String refreshToken) {
        redisRepository.setWithTimeout(getKey(accessToken), refreshToken,
                accessTokenValidityInSeconds);
    }

    public String getAt(String accessToken) {
        // 서비스 안에서 at 는 만료될 수 있기 때문에 에러를 던지지 않고 null 을 반환합니다.
        return redisRepository.get(getKey(accessToken)).orElse(null);
    }

    public void delete(String accessToken) {
        redisRepository.delete(getKey(accessToken));
    }

    public boolean isExist(String accessToken) {
        return redisRepository.get(getKey(accessToken)).isPresent();
    }
}
