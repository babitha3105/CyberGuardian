
        package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.entity.SearchHistory;
import com.example.cyberguardian.repository.ChildRepository;
import com.example.cyberguardian.repository.SearchHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryService {

    private final SearchHistoryRepository searchhistoryrepository;
    private final SearchAnalysisService searchAnalysisService;
    private final AlertService alertService;
    private final ChildRepository childRepository;

    public SearchHistoryService(
            SearchHistoryRepository searchhistoryrepository,
            SearchAnalysisService searchAnalysisService,
            AlertService alertService,
            ChildRepository childRepository) {

        this.searchhistoryrepository = searchhistoryrepository;
        this.searchAnalysisService = searchAnalysisService;
        this.alertService = alertService;
        this.childRepository = childRepository;
    }

    public SearchHistory saveSearchHistory(SearchHistory searchhistory) {

        // Get the actual child from database
        Child child = childRepository.findById(
                searchhistory.getChild().getChildId()
        ).orElseThrow(() -> new RuntimeException("Child not found"));

        // Attach the real child object
        searchhistory.setChild(child);

        // Analyze search query
        String category =
                searchAnalysisService.determineCategory(
                        searchhistory.getSearchQuery()
                );

        String riskLevel =
                searchAnalysisService.determineRiskLevel(
                        searchhistory.getSearchQuery()
                );

        // Store analysis result
        searchhistory.setCategory(category);
        searchhistory.setRiskLevel(riskLevel);

        // Save search history
        SearchHistory savedSearch =
                searchhistoryrepository.save(searchhistory);

        // Create alert for HIGH-risk search
        if ("HIGH".equals(riskLevel)) {

            Alert alert = new Alert();

            alert.setChild(child);
            alert.setParent(child.getParent());

            alert.setAlertType("HIGH_RISK_SEARCH");

            alert.setMessage(
                    "High-risk search detected: "
                            + searchhistory.getSearchQuery()
            );

            alert.setRead(false);

            alertService.saveAlert(alert);
        }

        return savedSearch;
    }

    public List<SearchHistory> getSearchHistoryByChildId(Long childId) {
        return searchhistoryrepository.findByChildChildId(childId);
    }

    public List<SearchHistory> getRecentSearches(Long childId) {

        java.time.LocalDateTime twentyFourHoursAgo =
                java.time.LocalDateTime.now().minusHours(24);

        return searchhistoryrepository
                .findByChildChildIdAndSearchedAtAfterOrderBySearchedAtDesc(
                        childId,
                        twentyFourHoursAgo
                );
    }
}
