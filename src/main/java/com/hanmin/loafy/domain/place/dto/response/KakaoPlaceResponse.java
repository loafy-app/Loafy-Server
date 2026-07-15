package com.hanmin.loafy.domain.place.dto.response;

import java.util.List;

public record KakaoPlaceResponse(
        List<PlaceResponse> documents
) {
}
