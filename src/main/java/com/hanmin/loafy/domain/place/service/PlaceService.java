package com.hanmin.loafy.domain.place.service;

import com.hanmin.loafy.domain.place.converter.PlaceConverter;
import com.hanmin.loafy.domain.place.dto.request.PlaceRequest;
import com.hanmin.loafy.domain.place.dto.request.PlaceSaveRequest;
import com.hanmin.loafy.domain.place.dto.response.KakaoPlaceResponse;
import com.hanmin.loafy.domain.place.entity.Place;
import com.hanmin.loafy.domain.place.repository.PlaceRepository;
import com.hanmin.loafy.global.kakao.KakaoLocalClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceService {

    private final KakaoLocalClient kakaoLocalClient;
    private final PlaceRepository placeRepository;

    // 위치 정보 저장
    public Place findOrCreatePlace(PlaceSaveRequest request) {

        Optional<Place> place = placeRepository.findPlaceByPlaceId(request.kakaoPlaceId());

        if (place.isPresent()) {
            log.info("[ PlaceService ]: DB에 장소 정보가 존재합니다. 장소 정보를 반홥힙니다.");
            return place.get();
        }
        else {
            log.info("[ PlaceService ]: DB에 해당 정보가 존재하지 않습니다. DB에 장소 정보를 저장 후 반환합니다.");
            Place tempPlace = PlaceConverter.kakaoToPlace(request);
            placeRepository.save(tempPlace);
            return tempPlace;
        }
    }

    // 위치 기반 장소 출력
    public KakaoPlaceResponse searchNearPlace(PlaceRequest request) {
        return kakaoLocalClient.searchNearCafe(request);
    }

}
