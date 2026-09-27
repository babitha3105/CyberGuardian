package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.service.HeartBeatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/heartbeat")
public class HeartBeatController {

    private final HeartBeatService heartBeatService;

    public HeartBeatController(HeartBeatService heartBeatService) {
        this.heartBeatService = heartBeatService;
    }

    @PostMapping
    public Child updateHeartbeat(@RequestParam Long childId) {

        return heartBeatService.updateHeartbeat(childId);
    }
}