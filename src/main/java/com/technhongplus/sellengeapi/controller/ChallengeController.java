package com.technhongplus.sellengeapi.controller;

import com.technhongplus.sellengeapi.dto.ChallengeDto;
import com.technhongplus.sellengeapi.dto.JoinChallengeDto;
import com.technhongplus.sellengeapi.dto.StatDto;
import com.technhongplus.sellengeapi.dto.exception.ExceptionDto;
import com.technhongplus.sellengeapi.dto.exception.JoinAlreadyException;
import com.technhongplus.sellengeapi.entity.Challenge;
import com.technhongplus.sellengeapi.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 전체 챌린지 조회
     */
    @GetMapping("/challenge")
    public ResponseEntity<?> getChallenges() {
        List<ChallengeDto> challengeDtos = challengeService.findChallenge();
        return ResponseEntity.ok().body(challengeDtos);
    }

    /**
     * 회원이 챌린지에 참가한다.
     */
    @PostMapping("/challenge/join/{id}")
    public ResponseEntity<?> joinChallenge(@RequestHeader("Login_Member_Id") Long loginMemberId, @PathVariable(name = "id") Long challengeId, @RequestBody JoinChallengeDto joinChallengeDto) {
        Long id = challengeService.joinChallenge(loginMemberId, challengeId, joinChallengeDto);
        return ResponseEntity.ok().body(id);
    }

    @ExceptionHandler(JoinAlreadyException.class)
    public ResponseEntity<?> joinException(JoinAlreadyException e) {
        return ResponseEntity.ok().body(new ExceptionDto("500", e.getMessage()));
    }

    @GetMapping("/challenge/stat")
    public ResponseEntity<?> getStat(@RequestHeader("Login_Member_Id") Long loginMemberId) {
        StatDto statDto = challengeService.getStat(loginMemberId);
        return ResponseEntity.ok().body(statDto);
    }
}
