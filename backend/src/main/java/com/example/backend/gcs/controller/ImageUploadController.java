package com.example.backend.gcs.controller;

import com.example.backend.gcs.dto.ImageUploadRequestDto;
import com.example.backend.gcs.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    @GetMapping("/image-upload")
    public ResponseEntity<String> uploadImage(ImageUploadRequestDto imageUploadRequestDto) {
        String url = imageUploadService.uploadImage(imageUploadRequestDto);
        return ResponseEntity.ok(url);
    }
}
