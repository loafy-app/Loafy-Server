package com.hanmin.loafy.domain.member.controller;

import com.hanmin.loafy.domain.member.dto.request.SignupRequest;
import com.hanmin.loafy.domain.member.dto.request.UpdateNicknameRequest;
import com.hanmin.loafy.domain.member.dto.request.UpdatePasswordRequest;
import com.hanmin.loafy.domain.member.dto.response.InfoResponse;
import com.hanmin.loafy.domain.member.service.MemberService;
import com.hanmin.loafy.global.CustomResponse;
import com.hanmin.loafy.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "Member API", description = "사용자 관련 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입 API입니다.")
    @PostMapping("")
    public CustomResponse<?> signup(@RequestBody SignupRequest request) {
        memberService.createMember(request);
        return CustomResponse.onSuccess("회원가입 완료");
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴 API 입니다.")
    @DeleteMapping("/me")
    public CustomResponse<?> withdraw(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();
        memberService.withdraw(email);
        return CustomResponse.onSuccess("회원탈퇴 완료");
    }

    @Operation(summary = "회원정보 조회", description = "회원정보 조회 API 입니다.")
    @GetMapping("/me")
    public CustomResponse<?> showInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();
        InfoResponse infoResponse = memberService.showMemberInfo(email);
        return CustomResponse.onSuccess(infoResponse);
    }

    @Operation(summary = "닉네임 변경", description = "닉네임 변경 API 입니다.")
    @PatchMapping("/me/nickname")
    public CustomResponse<?> updateNickname(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            UpdateNicknameRequest request) {
        String email = userDetails.getUsername();
        memberService.updateNickname(request, email);
        return CustomResponse.onSuccess("닉네임 변경 완료");
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API 입니다.")
    @PatchMapping("/me/password")
    public CustomResponse<?> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            UpdatePasswordRequest request) {
        String email = userDetails.getUsername();
        memberService.updatePassword(request, email);
        return CustomResponse.onSuccess("비밀번호 변경 완료");
    }

}
