package com.hanmin.loafy.global.kakao;

import com.hanmin.loafy.domain.place.dto.request.PlaceRequest;
import com.hanmin.loafy.domain.place.dto.response.KakaoPlaceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class KakaoLocalClient {

    private final RestClient restClient;
    private static final String CAFE_CATEGORY_CODE = "CE7";

    public KakaoLocalClient(RestClient.Builder restClientBuilder,
                            @Value("${kakao.rest-api-key}") String restApiKey) {
        this.restClient = restClientBuilder
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "KakaoAK " + restApiKey
                )
                .build();
    }

    public KakaoPlaceResponse searchNearCafe(PlaceRequest placeRequest) {
        KakaoPlaceResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/category.json")
                        .queryParam("category_group_code", CAFE_CATEGORY_CODE)
                        .queryParam("x", placeRequest.longitude())
                        .queryParam("y", placeRequest.latitude())
                        .queryParam("radius", placeRequest.radius())
                        .queryParam("sort", "distance")
                        .build())
                .retrieve()
                .body(KakaoPlaceResponse.class);
        log.info("[ KakaoLocalClient ]: 카페 정보 조회 및 파싱 완료");
        return response;
    }

}
