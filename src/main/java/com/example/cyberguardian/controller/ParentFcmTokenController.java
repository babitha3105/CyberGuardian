package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.ParentFcmToken;
import com.example.cyberguardian.service.ParentFcmTokenService;
import org.springframework.web.bind.annotation.*;
import com.example.cyberguardian.service.FcmNotificationService;
@RestController
@RequestMapping("/api/fcm")
public class ParentFcmTokenController {

    private final ParentFcmTokenService tokenService;
    private final FcmNotificationService notificationService;
    public ParentFcmTokenController(
            ParentFcmTokenService tokenService, FcmNotificationService notificationService) {

        this.tokenService = tokenService;
        this.notificationService = notificationService;
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
    @PostMapping("/test")
    public String testNotification(
            @RequestParam String fcmToken) {

        return notificationService.sendNotification(
                fcmToken,
                "CyberGuardian Test",
                "FCM notification is working!"
        );
    }
}