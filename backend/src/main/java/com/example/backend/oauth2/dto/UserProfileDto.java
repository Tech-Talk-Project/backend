package com.example.backend.oauth2.dto;

import com.example.backend.oauth2.exception.OAuth2InvalidEmailException;
import lombok.Data;

@Data
public class UserProfileDto {
    private String name;
    private String email;
    private String imageUrl;

    public UserProfileDto(String name, String email, String imageUrl) {
        this.name = name;

        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new OAuth2InvalidEmailException("there is no email in profile");
        } else {
            this.email = email;
        }

        this.imageUrl = imageUrl;
    }
}
