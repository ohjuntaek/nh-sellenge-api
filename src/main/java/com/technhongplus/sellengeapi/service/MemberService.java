package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.entity.Member;
import com.technhongplus.sellengeapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입, 로그인 구현

    @Transactional
    public void init() {
        Member seller = Member.of("seller", "판매자", "0000000000", true);
        Member member = Member.of("user", "일반회원", "0000000000", false);
        memberRepository.save(seller);
        memberRepository.save(member);
    }
}
