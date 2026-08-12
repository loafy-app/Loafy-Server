package com.hanmin.loafy.domain.place.controller;

import com.hanmin.loafy.domain.place.dto.request.PlaceSaveRequest;
import com.hanmin.loafy.domain.place.service.PlaceScrapService;
import com.hanmin.loafy.global.CustomResponse;
import com.hanmin.loafy.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/scrap")
@RequiredArgsConstructor
@Tag(name = "Scrap API", description = "장소 즐겨찾기 관련 API")
public class PlaceScrapController {

    private final PlaceScrapService placeScrapService;

    @Operation(summary = "즐겨찾기 API", description = "즐겨찾기 API입니다.")
    @PostMapping("/toggle")
    public CustomResponse<?> scrap(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestBody PlaceSaveRequest request) {

        placeScrapService.placeScrapToggle(request, userDetails.getUsername());

        return CustomResponse.onSuccess("즐겨찾기 추가/삭제 왼료");
    }

}
