package com.example.backend.oauth2.util.userprofile;

import com.example.backend.oauth2.dto.UserProfileDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NaverUserProfileExtractor implements UserProfileExtractor{
    @Override
    public UserProfileDto extract(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String name = (String) response.get("name");
        String email = (String) response.get("email");
        String imageUrl = (String) response.get("profile_image");

        return new UserProfileDto(name, email, imageUrl);
    }
}
