package com.example.backend.oauth2.api;

import com.example.backend.oauth2.util.property.OAuth2Property;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class OAuth2UserAttributesFetcher {
    public Map<String, Object> fetch(String accessToken, OAuth2Property oAuth2Property) {
        return WebClient.create()
                .post()
                .uri(oAuth2Property.getUserInfoUri())
                .headers(header -> {
                    header.setBearerAuth(accessToken);
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
