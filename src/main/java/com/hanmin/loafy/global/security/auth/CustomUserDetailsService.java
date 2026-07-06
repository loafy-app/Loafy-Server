package com.hanmin.loafy.global.security.auth;

import com.hanmin.loafy.domain.member.entity.Member;
import com.hanmin.loafy.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final MemberRepository memberRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(" [ CustomUserDetailsService ]: Email을 이용하여 사용자를 검색합니다.");
        Optional<Member> userEntity = memberRepository.findByEmail(email);
        if (userEntity.isPresent()) {
            Member member = userEntity.get();
            return new CustomUserDetails(member.getEmail(), member.getPassword(), member.getRole());
        }
        throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
    }

}
