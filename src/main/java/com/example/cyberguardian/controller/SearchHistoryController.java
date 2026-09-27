package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.SearchHistory;
import com.example.cyberguardian.service.SearchHistoryService;
import com.example.cyberguardian.service.GeminiSearchAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.cyberguardian.dto.GeminiSearchAnalysisDTO;
@RestController
@RequestMapping("/api/search-history")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;
    private final GeminiSearchAnalysisService geminiSearchAnalysisService;

    public SearchHistoryController(
            SearchHistoryService searchHistoryService,
            GeminiSearchAnalysisService geminiSearchAnalysisService) {

        this.searchHistoryService = searchHistoryService;
        this.geminiSearchAnalysisService = geminiSearchAnalysisService;
    }

    @PostMapping
    public SearchHistory saveSearchHistory(
            @RequestBody SearchHistory searchHistory) {

        return searchHistoryService.saveSearchHistory(searchHistory);
    }

    @GetMapping("/child/{childId}")
    public List<SearchHistory> getSearchHistoryByChildId(
            @PathVariable Long childId) {

        return searchHistoryService
                .getSearchHistoryByChildId(childId);
    }

    @GetMapping("/analyze/{childId}")
    public GeminiSearchAnalysisDTO analyzeRecentSearches(
            @PathVariable Long childId) {

        List<SearchHistory> recentSearches =
                searchHistoryService.getRecentSearches(childId);

        return geminiSearchAnalysisService
                .analyzeRecentSearchHistory(childId, recentSearches);    }
}