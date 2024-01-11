package com.example.backend.gcs.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadRequestDto {
    @NotNull
    private MultipartFile image;
}
