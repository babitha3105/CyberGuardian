package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.BrowsingHistory;
import com.example.cyberguardian.entity.SearchHistory;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.dto.WeeklyReport;
import java.util.List;
import com.example.cyberguardian.repository.BrowsingHistoryRepository;
import com.example.cyberguardian.repository.SearchHistoryRepository;
import com.example.cyberguardian.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WeeklyReportService {

    private final BrowsingHistoryRepository browsinghistoryrepository;
    private final SearchHistoryRepository searchhistoryrepository;
    private final AlertRepository alertrepository;

    public WeeklyReportService(
            BrowsingHistoryRepository browsinghistoryrepository,
            SearchHistoryRepository searchhistoryrepository,
            AlertRepository alertrepository) {

        this.browsinghistoryrepository = browsinghistoryrepository;
        this.searchhistoryrepository = searchhistoryrepository;
        this.alertrepository = alertrepository;
    }
    public WeeklyReport generateReport(Long childId) {

        LocalDateTime sevenDaysAgo =
                LocalDateTime.now().minusDays(7);

        List<BrowsingHistory> browsingHistory =
                browsinghistoryrepository
                        .findByChildChildIdAndVisitedAtAfter(
                                childId,
                                sevenDaysAgo
                        );

        List<SearchHistory> searchHistory =
                searchhistoryrepository
                        .findByChildChildIdAndSearchedAtAfterOrderBySearchedAtDesc(
                                childId,
                                sevenDaysAgo
                        );

        List<Alert> alerts =
                alertrepository
                        .findByChildChildIdAndCreatedAtAfter(
                                childId,
                                sevenDaysAgo
                        );

        int totalWebsites = browsingHistory.size();

        int totalSearches = searchHistory.size();

        int totalAlerts = alerts.size();
        long blockedWebsites = browsingHistory.stream()
                .filter(history ->
                        "BLOCK".equals(history.getActionTaken()))
                .count();
        long highRiskSearches = searchHistory.stream()
                .filter(search ->
                        "HIGH".equals(search.getRiskLevel()))
                .count();
        long educationSearches = searchHistory.stream()
                .filter(search ->
                        "EDUCATION".equals(search.getCategory()))
                .count();
        long sportsSearches = searchHistory.stream()
                .filter(search ->
                        "SPORTS".equals(search.getCategory()))
                .count();
        long entertainmentSearches = searchHistory.stream()
                .filter(search ->
                        "ENTERTAINMENT".equals(search.getCategory()))
                .count();
        long gamingSearches = searchHistory.stream()
                .filter(search ->
                        "GAMING".equals(search.getCategory()))
                .count();
        long otherSearches = searchHistory.stream()
                .filter(search ->
                        "OTHER".equals(search.getCategory()))
                .count();
        WeeklyReport report = new WeeklyReport();

        report.setTotalWebsites(totalWebsites);
        report.setTotalSearches(totalSearches);
        report.setTotalAlerts(totalAlerts);

        report.setBlockedWebsites(blockedWebsites);
        report.setHighRiskSearches(highRiskSearches);

        report.setEducationSearches(educationSearches);
        report.setSportsSearches(sportsSearches);
        report.setEntertainmentSearches(entertainmentSearches);
        report.setGamingSearches(gamingSearches);
        report.setOtherSearches(otherSearches);
        if (highRiskSearches >= 3 || totalAlerts >= 3) {
            report.setSafetyStatus("HIGH_RISK");
        } else if (highRiskSearches > 0 || totalAlerts > 0) {
            report.setSafetyStatus("MODERATE");
        } else {
            report.setSafetyStatus("SAFE");
        }
        return report;
    }
}