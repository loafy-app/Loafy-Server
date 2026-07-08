package com.hanmin.loafy.domain.member.service;

import com.hanmin.loafy.domain.auth.service.AuthService;
import com.hanmin.loafy.domain.member.converter.MemberConverter;
import com.hanmin.loafy.domain.member.dto.request.SignupRequest;
import com.hanmin.loafy.domain.member.dto.request.UpdateNicknameRequest;
import com.hanmin.loafy.domain.member.dto.request.UpdatePasswordRequest;
import com.hanmin.loafy.domain.member.dto.response.InfoResponse;
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
        log.info("[ MemberService ]: 로그아웃 처리가 완료되었습니다.");
    }

    // 회원 정보 조회
    public InfoResponse showMemberInfo(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberConverter.toInfoResponse(member);
    }

    // 닉네임 변경
    public void updateNickname(UpdateNicknameRequest request, String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        if (request.newNickname().equals(member.getNickname())) {
            throw new MemberException(MemberErrorCode.SAME_NICKNAME);
        }
        member.updateNickname(request.newNickname());
        log.info("[ MemberService ]: 닉네임이 변경되었습니다.");
    }

    public void updatePassword(UpdatePasswordRequest request, String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        // 확인용 패스워드 동일 여부 검증
        if (!request.newPassword().equals(request.newPasswordConfirm())) {
            throw new MemberException(MemberErrorCode.PASSWORD_MISMATCH);
        }
        // 기존의 패스워드와의 동일 여부 검증
        if (passwordEncoder.matches(request.newPassword(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.PASSWORD_NOT_CHANGED);
        }
        member.updatePassword(passwordEncoder.encode(request.newPassword()));
        log.info("[ MemberService ]: 비밀번호가 변경되었습니다.");
    }

}
