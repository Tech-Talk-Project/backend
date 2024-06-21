package com.example.backend.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDescRequestDto {
    @NotNull
    String detailedDescription;

    public UpdateDescRequestDto(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }
}
