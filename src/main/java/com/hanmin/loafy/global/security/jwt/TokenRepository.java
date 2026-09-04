package com.hanmin.loafy.global.security.jwt;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findTokenByMemberId(Long memberId);
    boolean existsById(@NonNull Long memberId);
    boolean existsByRefreshToken(String refreshToken);

    @Query("select t.memberId from Token t where t.refreshToken = :refreshToken")
    Long findMemberIdByRefreshToken(String refreshToken);

}
