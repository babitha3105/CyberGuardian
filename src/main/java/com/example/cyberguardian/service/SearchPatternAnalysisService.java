package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.entity.SearchHistory;
import com.example.cyberguardian.repository.ChildRepository;
import com.example.cyberguardian.repository.SearchHistoryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class SearchPatternAnalysisService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final AlertService alertService;
    private final ChildRepository childRepository;

    public SearchPatternAnalysisService(
            SearchHistoryRepository searchHistoryRepository,
            AlertService alertService,
            ChildRepository childRepository) {

        this.searchHistoryRepository = searchHistoryRepository;
        this.alertService = alertService;
        this.childRepository = childRepository;
    }

    public String analyzePattern(Long childId) {

        LocalDateTime sevenDaysAgo =
                LocalDateTime.now().minusDays(7);

        List<SearchHistory> searches =
                searchHistoryRepository
                        .findByChildChildIdAndSearchedAtAfterOrderBySearchedAtDesc(
                                childId,
                                sevenDaysAgo
                        );

        int phishingCount = 0;
        int gamblingCount = 0;
        int scamCount = 0;
        int malwareCount = 0;

        for (SearchHistory search : searches) {

            String query =
                    search.getSearchQuery().toLowerCase();

            if (query.contains("phishing") ||
                    query.contains("fake login") ||
                    query.contains("steal password")) {

                phishingCount++;
            }

            if (query.contains("betting") ||
                    query.contains("gambling") ||
                    query.contains("casino")) {

                gamblingCount++;
            }

            if (query.contains("scam") ||
                    query.contains("fake otp") ||
                    query.contains("fraud")) {

                scamCount++;
            }

            if (query.contains("malware") ||
                    query.contains("ransomware") ||
                    query.contains("virus")) {

                malwareCount++;
            }
        }

        if (phishingCount >= 3) {

            createPatternAlert(
                    childId,
                    "PHISHING_PATTERN",
                    "Repeated phishing-related searches detected"
            );

            return "PHISHING_PATTERN";
        }

        if (gamblingCount >= 3) {

            createPatternAlert(
                    childId,
                    "GAMBLING_PATTERN",
                    "Repeated gambling-related searches detected"
            );

            return "GAMBLING_PATTERN";
        }

        if (scamCount >= 3) {

            createPatternAlert(
                    childId,
                    "SCAM_PATTERN",
                    "Repeated scam-related searches detected"
            );

            return "SCAM_PATTERN";
        }

        if (malwareCount >= 3) {

            createPatternAlert(
                    childId,
                    "MALWARE_PATTERN",
                    "Repeated malware-related searches detected"
            );

            return "MALWARE_PATTERN";
        }

        return "NO_PATTERN";
    }

    private void createPatternAlert(
            Long childId,
            String alertType,
            String message) {

        if (alertService.alertExists(childId, alertType)) {
            return;
        }

        Child child = childRepository.findById(childId)
                .orElseThrow(() ->
                        new RuntimeException("Child not found"));

        Alert alert = new Alert();

        alert.setChild(child);
        alert.setParent(child.getParent());
        alert.setAlertType(alertType);
        alert.setMessage(message);
        alert.setRead(false);

        alertService.saveAlert(alert);
    }
}