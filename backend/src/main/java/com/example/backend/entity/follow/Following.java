package com.example.backend.entity.follow;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
@Getter
public class Following {
    @Id
    private Long id;

    private List<Long> followingIds = new ArrayList<>();

    public Following(Long id) {
        this.id = id;
    }
}
