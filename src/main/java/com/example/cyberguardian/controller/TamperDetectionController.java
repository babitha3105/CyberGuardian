package com.example.cyberguardian.controller;

import com.example.cyberguardian.service.TamperDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tamper-check")
public class TamperDetectionController {

    private final TamperDetectionService tamperDetectionService;

    public TamperDetectionController(
            TamperDetectionService tamperDetectionService) {

        this.tamperDetectionService = tamperDetectionService;
    }

    @GetMapping
    public boolean checkHeartbeat(
            @RequestParam Long childId) {

        return tamperDetectionService
                .isHeartbeatStale(childId);
    }
}