package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberSearchResponseDto {

    private List<SimpleProfile> data = new ArrayList<>();
    private int count;

    @Getter @Setter
    private static class SimpleProfile {
        private Long memberId;
        private String name;
        private String email;
        private String imageUrl;

        public SimpleProfile(Member member) {
            this.memberId = member.getId();
            this.name = member.getName();
            this.email = member.getEmail();
            this.imageUrl = member.getImageUrl();
        }
    }

    public MemberSearchResponseDto(List<Member> members) {
        for (Member member : members) {
            this.data.add(new SimpleProfile(member));
        }
        this.count = members.size();
    }
}
