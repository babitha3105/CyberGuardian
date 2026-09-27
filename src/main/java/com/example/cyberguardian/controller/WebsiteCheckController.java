package com.example.cyberguardian.controller;
import com.example.cyberguardian.dto.WebsiteCheckResponse;
import com.example.cyberguardian.service.WebsiteCheckService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/website-check")
public class WebsiteCheckController {

    private final WebsiteCheckService websitecheckservice;

    public WebsiteCheckController(WebsiteCheckService websiteCheckService) {
        this.websitecheckservice = websiteCheckService;
    }

    @GetMapping
    public WebsiteCheckResponse checkWebsite(
            @RequestParam Long childId,
            @RequestParam String url) {

        return websitecheckservice.checkWebsite(childId, url);
    }
}
