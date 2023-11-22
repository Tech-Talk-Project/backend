package com.example.backend.security.jwt;

import com.example.backend.security.exception.InvalidJwtException;
import com.example.backend.security.repository.AccessTokenRepository;
import com.example.backend.security.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtValidator {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtClaimReader jwtClaimReader;

    private boolean validateToken(String jwtToken) {
        try {
            jwtClaimReader.getClaims(jwtToken);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature.");
            throw new InvalidJwtException(jwtToken, "Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
            throw new InvalidJwtException(jwtToken, "Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
            throw new InvalidJwtException(jwtToken, "Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
            throw new InvalidJwtException(jwtToken, "Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.");
            throw new InvalidJwtException(jwtToken, "JWT claims string is empty.");
        }
        return true;
    }

    public boolean validateRefreshToken(String refreshToken) {
        validateToken(refreshToken);
        if (!refreshTokenRepository.isExist(refreshToken)) {
            throw new InvalidJwtException(refreshToken, "refresh token expired or deleted.");
        }
        return true;
    }

    public boolean validateAccessToken(String accessToken) {
        validateToken(accessToken);
        if (!accessTokenRepository.isExist(accessToken)) {
            throw new InvalidJwtException(accessToken, "access token expired or deleted.");
        }
        return true;
    }
}
