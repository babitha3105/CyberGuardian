package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.ParentFcmToken;
import com.example.cyberguardian.service.ParentFcmTokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
public class ParentFcmTokenController {

    private final ParentFcmTokenService tokenService;

    public ParentFcmTokenController(
            ParentFcmTokenService tokenService) {

        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ParentFcmToken saveToken(
            @RequestParam Long parentId,
            @RequestParam String fcmToken) {

        return tokenService.saveToken(
                parentId,
                fcmToken
        );
    }
}