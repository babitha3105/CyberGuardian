package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.BrowsingHistory;
import com.example.cyberguardian.service.BrowsingHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/browsing-history")
public class BrowsingHistoryController {

    private final BrowsingHistoryService browsinghistoryservice;

    public BrowsingHistoryController(BrowsingHistoryService browsinghistoryservice) {
        this.browsinghistoryservice = browsinghistoryservice;
    }

    @PostMapping
    public BrowsingHistory saveBrowsingHistory(@RequestBody BrowsingHistory browsingHistory) {
        return browsinghistoryservice.saveBrowsingHistory(browsingHistory);
    }

    @GetMapping("/child/{childId}")
    public List<BrowsingHistory> getBrowsingHistoryByChildId(@PathVariable Long childId) {
        return browsinghistoryservice.getBrowsingHistoryByChildId(childId);
    }
}