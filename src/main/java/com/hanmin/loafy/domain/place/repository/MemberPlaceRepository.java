package com.hanmin.loafy.domain.place.repository;

import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.domain.place.entity.MemberPlace;
import com.hanmin.loafy.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPlaceRepository extends JpaRepository<MemberPlace, Long> {

    Optional<MemberPlace> findMemberPlaceByMemberAndPlace(Member member, Place place);

}
