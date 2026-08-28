package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.WebsiteList;
import com.example.cyberguardian.service.WebsiteListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/website-lists")
public class WebsiteListController {

    private final WebsiteListService websitelistservice;

    public WebsiteListController(WebsiteListService websiteListService) {
        this.websitelistservice = websiteListService;
    }

    @PostMapping
    public WebsiteList saveWebsiteList(@RequestBody WebsiteList websiteList) {
        return websitelistservice.saveWebsiteList(websiteList);
    }

    @GetMapping
    public List<WebsiteList> getAllWebsiteLists() {
        return websitelistservice.getAllWebsiteLists();
    }
}