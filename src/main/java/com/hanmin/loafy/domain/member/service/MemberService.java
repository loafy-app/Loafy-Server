package com.hanmin.loafy.domain.member.service;

import com.hanmin.loafy.domain.member.converter.MemberConverter;
import com.hanmin.loafy.domain.member.dto.request.SignupRequest;
import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.domain.member.repository.MemberRepository;
import com.hanmin.loafy.global.code.AuthErrorCode;
import com.hanmin.loafy.global.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void createMember(SignupRequest request) {
        Member member = MemberConverter.toMember(request, passwordEncoder);
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            log.info("[ MemberService ]: 이미 존재하는 회원입니다.");
            throw new MemberException(AuthErrorCode.BAD_REQUEST_400);
        }
        else {
            memberRepository.save(member);
        }

    }

}
