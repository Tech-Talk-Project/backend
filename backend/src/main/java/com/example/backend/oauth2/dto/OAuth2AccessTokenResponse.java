package com.example.backend.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OAuth2AccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
