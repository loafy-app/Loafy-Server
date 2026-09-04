package com.hanmin.loafy.domain.place.converter;

import com.hanmin.loafy.domain.place.dto.request.PlaceSaveRequest;
import com.hanmin.loafy.domain.place.entity.Place;

public class PlaceConverter {

    public static Place kakaoToPlace(PlaceSaveRequest request) {
        return Place.builder()
                .kakaoPlaceId(request.kakaoPlaceId())
                .placeName(request.placeName())
                .addressName(request.addressName())
                .roadAddressName(request.roadAddressName())
                .phone(request.phone())
                .x(request.x())
                .y(request.y())
                .build();
    }

}
