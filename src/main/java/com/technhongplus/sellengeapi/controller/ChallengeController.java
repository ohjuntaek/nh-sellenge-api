package com.technhongplus.sellengeapi.controller;

import com.technhongplus.sellengeapi.dto.ChallengeDto;
import com.technhongplus.sellengeapi.dto.JoinChallengeDto;
import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    /**
     * 판매자가 챌린지를 등록한다.
     */
    @PostMapping("/challenge/register")
    public ResponseEntity<?> registerChallenge(@RequestHeader("Login_Member_Id") Long loginMemberId, @RequestBody ChallengeDto challengeDto) {
        Long id = challengeService.registerChallenge(loginMemberId, challengeDto);
        return ResponseEntity.ok().body(id);
    }

    /**
     * 회원이 챌린지에 참가한다.
     */
    @PostMapping("/challenge/join/{id}")
    public ResponseEntity<?> joinChallenge(@RequestHeader("Login_Member_Id") Long loginMemberId, @PathVariable(name = "id") Long challengeId, @RequestBody JoinChallengeDto joinChallengeDto) {
        Long id = challengeService.joinChallenge(loginMemberId, challengeId, joinChallengeDto);
        return ResponseEntity.ok().body(id);
    }
}
