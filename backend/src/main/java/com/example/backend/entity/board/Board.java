package com.example.backend.entity.board;

import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.member.Member;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Board extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    private Member author;
    private Long viewCount;
    private Long likeCount;
    private Long dislikeCount;
    private String comment;



    public void addTag(String tagName) {
        this.tags.add(new Tag(tagName, this));
    }


}
