package com.technhongplus.sellengeapi.controller;


import com.technhongplus.sellengeapi.dto.MissionDto;
import com.technhongplus.sellengeapi.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/mission/list")
    public ResponseEntity<?> getMissionList(@RequestParam(name = "challengeId") Long challengeId, @RequestParam(name = "date") LocalDateTime date) {
        return ResponseEntity.ok().body(missionService.getMission(challengeId,date));
    }

    @PostMapping("/mission")
    public ResponseEntity<?> registerMission(@RequestHeader("Login_Member_Id") Long loginMemberId, @RequestBody MissionDto missionDto) {
        missionService.registerMission(loginMemberId,missionDto);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/mission/certification")
    public ResponseEntity<?> certifiedMission(@RequestHeader("Login_Member_Id") Long loginMemberId,
                                              @RequestParam(name = "challengeId") Long challengeId,
                                              @RequestParam(name = "success") boolean success){
        missionService.certifiedMission(loginMemberId,challengeId,success);
        return ResponseEntity.ok().body("ok");
    }

}
