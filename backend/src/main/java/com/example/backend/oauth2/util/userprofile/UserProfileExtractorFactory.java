package com.example.backend.oauth2.util.userprofile;


import com.example.backend.oauth2.OAuth2Provider;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserProfileExtractorFactory {
    public static UserProfileExtractor get(OAuth2Provider oAuth2Provider) {
        switch (oAuth2Provider) {
            case GOOGLE:
                return new GoogleUserProfileExtractor();
            case NAVER:
                return new NaverUserProfileExtractor();
            case GITHUB:
                return new GitHubUserProfileExtractor();
            default:
                throw new IllegalArgumentException("Invalid OAuth2 Provider");
        }
    }
}
