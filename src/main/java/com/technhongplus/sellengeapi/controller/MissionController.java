package com.technhongplus.sellengeapi.controller;


import com.technhongplus.sellengeapi.dto.MissionDto;
import com.technhongplus.sellengeapi.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/mission/list")
    public ResponseEntity<?> getMissionList(@RequestParam(name = "challengeId") Long challengeId, @RequestParam(name = "date") String date) {
        return ResponseEntity.ok().body(missionService.getMission(challengeId,getLocalDate(date)));
    }

    @PostMapping("/mission")
    public ResponseEntity<?> registerMission(@RequestHeader("Login_Member_Id") Long loginMemberId, @RequestBody MissionDto missionDto) {
        missionService.registerMission(loginMemberId,missionDto);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/mission/certification")
    public ResponseEntity<?> certifiedMission(@RequestHeader("Login_Member_Id") Long loginMemberId,
                                              @RequestParam(name = "challengeId") Long challengeId,
                                              @RequestParam(name = "success") boolean success,
                                              @RequestParam(name = "date") String date){
        missionService.certifiedMission(loginMemberId,challengeId,success,getLocalDate(date));
        return ResponseEntity.ok().body("ok");
    }

    private LocalDate getLocalDate(String date){
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(4,6));
        int day = Integer.parseInt(date.substring(6,8));
        return LocalDate.of(year,month,day);
    }

}
