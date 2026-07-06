package com.hanmin.loafy.global.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t WhERE t.email = :email")
    Optional<Token> findByEmail(@Param("email") String email);

    boolean existsByEmail(String refreshToken);

}
