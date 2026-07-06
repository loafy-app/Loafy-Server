package com.hanmin.loafy.domain.member.repository;

import com.hanmin.loafy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m Where m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);

}
