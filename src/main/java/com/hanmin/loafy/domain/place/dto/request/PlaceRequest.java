package com.hanmin.loafy.domain.place.dto.request;

public record PlaceRequest(
        double latitude,
        double longitude,
        int radius
) {
}
