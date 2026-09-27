package com.example.cyberguardian.controller;

import com.example.cyberguardian.service.SafeBrowsingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/safe-browsing")
public class SafeBrowsingController {

    private final SafeBrowsingService safebrowsingservice;

    public SafeBrowsingController(SafeBrowsingService safeBrowsingService) {
        this.safebrowsingservice = safeBrowsingService;
    }

    @GetMapping("/check")
    public boolean check(@RequestParam String url) {
        return safebrowsingservice.isThreat(url);
    }
}