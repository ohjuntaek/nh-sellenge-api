package com.technhongplus.sellengeapi;

import com.technhongplus.sellengeapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Init {
    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.init();
    }
}
