package com.example.backend.controller.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateLinksRequestDto {
    List<String> links;

    public UpdateLinksRequestDto(List<String> links) {
        this.links = links;
    }
}
