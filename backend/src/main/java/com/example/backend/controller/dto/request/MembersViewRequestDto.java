package com.example.backend.controller.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class MembersViewRequestDto {
    String cursor;
    int limit;
    List<String> skills;

    public MembersViewRequestDto() {
        this.cursor = LocalDateTime.now().toString();
        this.limit = 15;
        this.skills = new ArrayList<>();
    }
}
