package com.example.cyberguardian.controller;

import com.example.cyberguardian.service.SearchPatternAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search-pattern")
public class SearchPatternAnalysisController {

    private final SearchPatternAnalysisService searchPatternAnalysisService;

    public SearchPatternAnalysisController(
            SearchPatternAnalysisService searchPatternAnalysisService) {

        this.searchPatternAnalysisService = searchPatternAnalysisService;
    }

    @GetMapping("/child/{childId}")
    public String analyzePattern(@PathVariable Long childId) {

        return searchPatternAnalysisService.analyzePattern(childId);
    }
}