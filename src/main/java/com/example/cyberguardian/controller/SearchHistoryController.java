package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.SearchHistory;
import com.example.cyberguardian.service.SearchHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search-history")
    public class SearchHistoryController {

        private final SearchHistoryService searchHistoryService;

        public SearchHistoryController(SearchHistoryService searchHistoryService) {
            this.searchHistoryService = searchHistoryService;
        }

        @PostMapping
        public SearchHistory saveSearchHistory(@RequestBody SearchHistory searchHistory) {
            return searchHistoryService.saveSearchHistory(searchHistory);
        }
    @GetMapping("/child/{childId}")
    public List<SearchHistory> getSearchHistoryByChildId(@PathVariable Long childId) {
        return searchHistoryService.getSearchHistoryByChildId(childId);
    }
    }
