package com.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/user")
    public Long user() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = (Long) principal;
        return memberId;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
