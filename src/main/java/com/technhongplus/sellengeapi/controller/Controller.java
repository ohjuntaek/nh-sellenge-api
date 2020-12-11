package com.technhongplus.sellengeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
    @GetMapping("/hello")
    public ResponseEntity<?> healthyCheck() {
        return ResponseEntity.ok().body("hi");
    }

}
