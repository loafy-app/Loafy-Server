package com.hanmin.loafy.domain.place.service;

import com.hanmin.loafy.domain.place.dto.request.PlaceRequest;
import com.hanmin.loafy.domain.place.dto.response.KakaoPlaceResponse;
import com.hanmin.loafy.global.kakao.KakaoLocalClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceService {

    private final KakaoLocalClient kakaoLocalClient;

    // 위치 기반 장소 출력
    public KakaoPlaceResponse searchNearPlace(PlaceRequest request) {
        return kakaoLocalClient.searchNearCafe(request);
    }

}
