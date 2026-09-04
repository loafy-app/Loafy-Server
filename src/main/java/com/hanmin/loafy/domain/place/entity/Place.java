package com.hanmin.loafy.domain.place.entity;

import com.hanmin.loafy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "place")
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private String placeId;
    private Long placeId;

    // 카카오 지도 API에서 자체적으로 다루는 장소 아이디
    @Column(name = "kakao_place_id")
    private String kakaoPlaceId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "place_url")
    private String placeURL;

    @Column(name = "address")
    private String addressName;

    @Column(name = "road_address_name")
    private String roadAddressName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

}
