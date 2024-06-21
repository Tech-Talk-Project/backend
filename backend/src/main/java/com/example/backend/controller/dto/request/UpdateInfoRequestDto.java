package com.example.backend.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateInfoRequestDto {
    @NotNull
    private String name;
    private String job;

    public UpdateInfoRequestDto(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
