package com.example.backend.oauth2.util.userprofile;


import com.example.backend.oauth2.OAuth2Provider;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileExtractorFactory {
    private final GoogleUserProfileExtractor googleUserProfileExtractor;
    private final NaverUserProfileExtractor naverUserProfileExtractor;
    private final GitHubUserProfileExtractor gitHubUserProfileExtractor;
    public UserProfileExtractor get(OAuth2Provider oAuth2Provider) {
        switch (oAuth2Provider) {
            case GOOGLE:
                return googleUserProfileExtractor;
            case NAVER:
                return naverUserProfileExtractor;
            case GITHUB:
                return gitHubUserProfileExtractor;
            default:
                throw new IllegalArgumentException("Invalid OAuth2 Provider");
        }
    }
}
