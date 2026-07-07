package com.hanmin.loafy.global.security.jwt;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
public class Token {

    @Id
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;
}
