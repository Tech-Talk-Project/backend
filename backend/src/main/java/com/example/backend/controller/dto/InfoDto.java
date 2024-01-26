package com.example.backend.controller.dto;

import lombok.Data;

@Data
public class InfoDto {
    private String name;
    private String job;
    private String email;

    public InfoDto(String name, String job, String email) {
        this.name = name;
        this.job = job;
        this.email = email;
    }
}
