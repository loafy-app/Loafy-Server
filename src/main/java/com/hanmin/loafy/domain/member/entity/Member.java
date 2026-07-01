package com.hanmin.loafy.domain.member.entity;

import com.hanmin.loafy.global.entity.BaseEntity;
import com.hanmin.loafy.global.security.auth.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Roles role;
}
