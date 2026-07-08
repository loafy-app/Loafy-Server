package com.hanmin.loafy.domain.member.entity;

import com.hanmin.loafy.global.entity.BaseEntity;
import com.hanmin.loafy.global.security.auth.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 auto increment의 역할
    @Column(name = "id") // DB의 id column과 매핑
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void withdraw() {
        isDeleted = true;
        deletedAt = LocalDateTime.now();
    }

    public void reactivate() {
        isDeleted = false;
        deletedAt = null;
    }
}
