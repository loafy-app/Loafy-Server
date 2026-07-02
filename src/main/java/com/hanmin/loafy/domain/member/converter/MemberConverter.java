package com.hanmin.loafy.domain.member.converter;

import com.hanmin.loafy.domain.member.dto.request.SignupRequest;
import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.global.security.auth.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;


public class MemberConverter {

    // request -> Member
    public static Member toMember(SignupRequest request, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(request.password());
        return Member.builder()
                .email(request.email())
                .password(encodedPassword)
                .role(Roles.ROLE_USER)
                .build();
    }

}
