package com.hanmin.loafy.domain.place.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlaceResponse(
        @JsonProperty("id")
        String id,

        @JsonProperty("place_name")
        String placeName,

        @JsonProperty("distance")
        String distance,

        @JsonProperty("place_url")
        String placeUrl,

        @JsonProperty("address_name")
        String addressName,

        @JsonProperty("road_address_name")
        String roadAddressName,

        @JsonProperty("phone")
        String phone,

        // 경도
        @JsonProperty("x")
        String x,

        // 위도
        @JsonProperty("y")
        String y

) {}
