package com.hanmin.loafy.domain.place.controller;

import com.hanmin.loafy.domain.place.dto.request.PlaceRequest;
import com.hanmin.loafy.domain.place.dto.response.KakaoPlaceResponse;
import com.hanmin.loafy.domain.place.service.PlaceService;
import com.hanmin.loafy.global.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@Tag(name = "Place API", description = "장소 관련 API")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "장소 출력 API", description = "장소 출력 API입니다.")
    @GetMapping("")
    public CustomResponse<KakaoPlaceResponse> searchNearPlace(@ModelAttribute PlaceRequest request
    ) {
        return CustomResponse.onSuccess(placeService.searchNearPlace(request));
    }

}
