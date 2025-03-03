package com.technhongplus.sellengeapi;

import com.technhongplus.sellengeapi.service.MemberService;
import com.technhongplus.sellengeapi.service.NhTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Init {
    private final MemberService memberService;
    private final NhTransactionService nhTransactionService;

    @PostConstruct
    public void init() {
//        memberService.init();
    }
}
