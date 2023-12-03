package com.example.backend.repository.member;

import com.example.backend.entity.member.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long>, MemberAuthorityQuerydsl {

}
