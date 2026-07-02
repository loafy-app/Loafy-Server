package com.hanmin.loafy.domain.member.controller;

import com.hanmin.loafy.domain.member.dto.request.SignupRequest;
import com.hanmin.loafy.domain.member.service.MemberService;
import com.hanmin.loafy.global.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입 API입니다.")
    @PostMapping("")
    public CustomResponse<?> signup(@RequestBody SignupRequest request) {
        memberService.createMember(request);
        return CustomResponse.onSuccess("회원가입 완료");
    }

}
