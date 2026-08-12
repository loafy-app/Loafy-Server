package com.hanmin.loafy.domain.place.service;

import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.domain.member.repository.MemberRepository;
import com.hanmin.loafy.domain.place.dto.request.PlaceSaveRequest;
import com.hanmin.loafy.domain.place.entity.MemberPlace;
import com.hanmin.loafy.domain.place.entity.Place;
import com.hanmin.loafy.domain.place.repository.MemberPlaceRepository;
import com.hanmin.loafy.global.code.MemberErrorCode;
import com.hanmin.loafy.global.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PlaceScrapService {

    private final MemberRepository memberRepository;
    private final PlaceService placeService;
    private final MemberPlaceRepository memberPlaceRepository;

    public void placeScrapToggle(PlaceSaveRequest request, String email) {

        // 장소 정보를 이용해서 장소 객체 생성
        Place place = placeService.findOrCreatePlace(request);

        // 인증 정보를 이용해서 멤버 객체 생성
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 즐겨찾기 테이블 존재여부 조회
        Optional<MemberPlace> existMemberPlace = memberPlaceRepository.findMemberPlaceByMemberAndPlace(member, place);

        // 즐겨찾기 테이블이 존재하지 않을 경우, 해당 엔티티를 삭제
        if (existMemberPlace.isEmpty()) {
                MemberPlace memberPlace = new MemberPlace(member, place);
                memberPlaceRepository.save(memberPlace);
                log.info("[ PlaceScrapService ]: 즐겨찾기 정보가 존재하지 않습니다. 즐겨찾기에 등록합니다");
        }
        // 즐겨찾기 테이블이 존재할 경우, 엔티티를 만들어 저장
        else {
            memberPlaceRepository.delete(existMemberPlace.get());
            log.info("[ PlaceScrapService ]: 즐겨찾기 정보가 존재합니다. 즐겨찾기를 해제합니다.");
        }
    }

}
