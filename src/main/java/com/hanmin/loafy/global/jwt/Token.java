package com.hanmin.loafy.global.jwt;

import com.hanmin.loafy.domain.member.entity.Member;
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

    @Setter
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
