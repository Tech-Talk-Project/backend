package com.example.backend.oauth2.util.property;

import com.example.backend.oauth2.OAuth2Provider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2")
@Getter @Setter
public class OAuth2Properties {

    public OAuth2Property get(OAuth2Provider oAuth2Provider) {
        switch (oAuth2Provider) {
            case GITHUB:
                return github;
            case NAVER:
                return naver;
            case GOOGLE:
                return google;
            default:
                throw new IllegalArgumentException("Invalid OAuth2 Provider");
        }
    }

    private Github github;
    private Naver naver;
    private Google google;

    @Getter @Setter
    private static class Github implements OAuth2Property {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String tokenUri;
        private String userInfoUri;
    }

    @Getter @Setter
    private static class Naver implements OAuth2Property {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String tokenUri;
        private String userInfoUri;
    }

    @Getter @Setter
    private static class Google implements OAuth2Property{
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String tokenUri;
        private String userInfoUri;
    }

}
