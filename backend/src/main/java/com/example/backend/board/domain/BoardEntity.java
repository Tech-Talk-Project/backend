package com.example.backend.board.domain;

import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BoardEntity {
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    String title;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member author;

    private Long viewCount = 0L;

    public void increaseViewCount() {
        viewCount++;
    }
}
