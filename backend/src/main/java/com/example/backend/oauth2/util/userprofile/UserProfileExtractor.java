package com.example.backend.oauth2.util.userprofile;

import com.example.backend.oauth2.dto.UserProfileDto;

import java.util.Map;

public interface UserProfileExtractor {
    UserProfileDto extract(Map<String, Object> attributes);
}
