package com.hanmin.loafy.domain.member.service;

import com.hanmin.loafy.domain.auth.service.AuthService;
import com.hanmin.loafy.domain.member.converter.MemberConverter;
import com.hanmin.loafy.domain.member.dto.request.SignupRequest;
import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.domain.member.repository.MemberRepository;
import com.hanmin.loafy.global.code.MemberErrorCode;
import com.hanmin.loafy.global.exception.AuthException;
import com.hanmin.loafy.global.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    // 회원가입
    public void createMember(SignupRequest request) {
        Optional<Member> optionalMember = memberRepository.findByEmail(request.email());
        if (memberRepository.existsByEmailAndIsDeletedFalse(request.email())) {
            log.info("[ MemberService ]: 이미 존재하는 회원입니다.");
            throw new MemberException(MemberErrorCode.DUPLICATE_MEMBER);
        }
        else if (memberRepository.existsByEmailAndIsDeletedTrue(request.email())) {
            optionalMember.ifPresent(Member::reactivate);
            log.info("[ MemberService ]: 회원 복구 처리가 완료되었습니다.");
        }
        else {
            Member member = MemberConverter.toMember(request, passwordEncoder);
            memberRepository.save(member);
            log.info("[ MemberService ]: 회원이 생성되었습니다.");
        }
    }

    // 회원 탈퇴
    public void withdraw(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new AuthException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.withdraw();
        log.info("[ MemberService ]: 사용자 탈퇴 처리가 완료되었습니다.");
        authService.logout(email);
    }

}
