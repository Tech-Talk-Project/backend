package com.example.backend.repository.member;

import com.example.backend.entity.member.Authority;
import com.example.backend.entity.member.EAuthority;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    @Query("select a from Authority a where a.eAuthority = :eAuthority")
    Optional<Authority> findByEAuthority(@Param("eAuthority") EAuthority eAuthority);
}
