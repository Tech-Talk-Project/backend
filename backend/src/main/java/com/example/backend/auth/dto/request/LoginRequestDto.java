package com.example.backend.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotNull
    String code;

    @NotNull
    String provider;
}
