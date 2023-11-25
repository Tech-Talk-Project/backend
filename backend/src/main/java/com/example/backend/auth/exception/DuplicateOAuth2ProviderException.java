package com.example.backend.auth.exception;

import com.example.backend.oauth2.OAuth2Provider;

public class DuplicateOAuth2ProviderException extends RuntimeException{
    public DuplicateOAuth2ProviderException(OAuth2Provider OAuth2Provider) {
        super("there is already an account registered with " + OAuth2Provider.name());
    }
}
