package com.hanmin.loafy.domain.place.dto.request;

public record PlaceSaveRequest(
        String placeId,
        String placeName,
        String distance,
        String placeUrl,
        String addressName,
        String roadAddressName,
        String phone,
        String x,
        String y
) {
}
