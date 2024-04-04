package com.example.backend.controller.dto.response;

import com.example.backend.controller.dto.ProfileDto;
import lombok.Data;

import java.util.List;

@Data
public class ProfilePaginationDto {
    List<ProfileDto> data;
    int count;
    String nextCursor;
}
