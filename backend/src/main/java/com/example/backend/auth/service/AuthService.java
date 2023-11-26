package com.example.backend.auth.service;

import com.example.backend.auth.dto.LoginSuccessDto;
import com.example.backend.auth.dto.RegisterSuccessDto;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.oauth2.service.OAuth2UserProfileService;
import com.example.backend.security.dto.AtRtDto;
import com.example.backend.security.jwt.JwtValidator;
import com.example.backend.security.repository.AccessTokenRepository;
import com.example.backend.security.repository.RefreshTokenRepository;
import com.example.backend.security.service.AtRtCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final OAuth2UserProfileService oAuth2UserProfileService;
    private final MemberRegisterService memberRegisterService;
    private final AtRtCreateService atRtCreateService;
    private final JwtValidator jwtValidator;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public LoginSuccessDto login(String code, OAuth2Provider OAuth2Provider) {
        UserProfileDto userProfile =
                oAuth2UserProfileService.getUserProfile(code, OAuth2Provider);
        RegisterSuccessDto registerSuccessDto =
                memberRegisterService.getOrCreateMember(userProfile, OAuth2Provider);

        Long memberId = registerSuccessDto.getMember().getId();
        AtRtDto atRtDto = atRtCreateService.create(memberId);
        return new LoginSuccessDto(registerSuccessDto, atRtDto);
    }

    public void logout(String accessToken, String refreshToken) {
        if (accessToken != null && refreshToken != null) {
            accessTokenRepository.delete(accessToken);
            refreshTokenRepository.delete(refreshToken);
        } else if (accessToken != null) {
            String pairRefreshToken = accessTokenRepository.getRt(accessToken);
            accessTokenRepository.delete(accessToken);
            refreshTokenRepository.delete(pairRefreshToken);
        } else if (refreshToken != null) {
            String pairAccessToken = refreshTokenRepository.getAt(refreshToken);
            accessTokenRepository.delete(pairAccessToken);
            refreshTokenRepository.delete(refreshToken);
        }
    }

    public AtRtDto refresh(String refreshToken) {
        jwtValidator.validateRefreshToken(refreshToken);
        return atRtCreateService.refresh(refreshToken);
    }
}
