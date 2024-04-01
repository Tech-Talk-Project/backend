package com.example.backend.entity.follow;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
public class Following {
    @Id
    private Long id;

    private Set<Long> followingIds = new HashSet<>();

    public Following(Long id) {
        this.id = id;
    }
}
