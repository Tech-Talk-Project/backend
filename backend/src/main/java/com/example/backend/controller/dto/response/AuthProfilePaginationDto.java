package com.example.backend.controller.dto.response;

import com.example.backend.controller.dto.ProfileDto;

import java.util.List;

public class AuthProfilePaginationDto {
    List<ProfileDto> data;
    private String nextCursor;
    private int count;

    public AuthProfilePaginationDto(List<ProfileDto> data, String nextCursor, int count) {
        this.data = data;
        this.nextCursor = nextCursor;
        this.count = count;
    }
}
