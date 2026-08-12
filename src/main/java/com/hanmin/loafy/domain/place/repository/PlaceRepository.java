package com.hanmin.loafy.domain.place.repository;

import com.hanmin.loafy.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    boolean existsPlacesByPlaceId(String placeId);

    Optional<Place> findPlaceByPlaceId(String placeId);

}
