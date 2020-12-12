package com.technhongplus.sellengeapi.service;

import com.technhongplus.sellengeapi.entity.Member;
import com.technhongplus.sellengeapi.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("NH_API_TEST - 투자금관리용가상계좌발급 + 핀어카운트 발급")
    @Commit
//    @Disabled
    void createVirtualAccountTest() {
//        Long id = memberService.sellerSignUp();
        Long id = memberService.signUp("user", "일반회원", "0000000000", "3020000003086", false);

        entityManager.flush();
        entityManager.clear();

        Member member = memberRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        // 가상계좌 세팅
//        assertThat(member.getFinAccount()).isNotBlank();
        assertThat(member.getVirtualAccount()).isNotBlank();
    }

    @Test
    void test() {
    }


}
