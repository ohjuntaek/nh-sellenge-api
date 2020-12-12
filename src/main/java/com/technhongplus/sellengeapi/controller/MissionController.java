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

    @GetMapping("/misson/list")
    public ResponseEntity<?> getMissionList(@PathVariable(name = "challengeId") Long challengeId, @PathVariable(name = "date") LocalDateTime date) {
        return ResponseEntity.ok().body(missionService.getMission(challengeId,date));
    }

    @PostMapping("/misson")
    public ResponseEntity<?> registerMission(@RequestHeader("Login_Member_Id") Long loginMemberId, @RequestBody MissionDto missionDto) {
        missionService.registerMission(loginMemberId,missionDto);
        return ResponseEntity.ok().body("ok");
    }

}
