package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProfilePaginationByUpdatedResponseDto extends ProfilePaginationResponseDto{
    private String nextCursor;

    public ProfilePaginationByUpdatedResponseDto(List<Member> members) {
        super(members);
        if (members.isEmpty()) {
            this.nextCursor = null;
            return;
        }
        this.nextCursor = members.get(members.size() - 1).getUpdatedAt().toString();
    }
}
