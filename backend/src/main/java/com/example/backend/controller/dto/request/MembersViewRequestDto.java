package com.example.backend.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MembersViewRequestDto {
    String cursor = LocalDateTime.now().toString();
    int limit = 15;
    @NotNull
    List<String> skills;
}
