package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.BrowsingHistory;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.repository.BrowsingHistoryRepository;
import com.example.cyberguardian.repository.ChildRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrowsingHistoryService {

    private final BrowsingHistoryRepository browsinghistoryrepository;
    private final WebsiteAnalysisService websiteAnalysisService;
    private final AlertService alertService;
    private final ChildRepository childRepository;

    public BrowsingHistoryService(
            BrowsingHistoryRepository browsinghistoryrepository,
            WebsiteAnalysisService websiteAnalysisService,
            AlertService alertService,
            ChildRepository childRepository) {

        this.browsinghistoryrepository = browsinghistoryrepository;
        this.websiteAnalysisService = websiteAnalysisService;
        this.alertService = alertService;
        this.childRepository = childRepository;
    }

    public BrowsingHistory saveBrowsingHistory(
            BrowsingHistory browsingHistory) {

        Child child = childRepository.findById(
                browsingHistory.getChild().getChildId()
        ).orElseThrow(() ->
                new RuntimeException("Child not found"));

        browsingHistory.setChild(child);

        String category =
                websiteAnalysisService.determineCategory(
                        browsingHistory.getUrl());

        String riskLevel = browsingHistory.getRiskLevel();

        browsingHistory.setCategory(category);
        browsingHistory.setRiskLevel(riskLevel);

        BrowsingHistory savedHistory =
                browsinghistoryrepository.save(browsingHistory);

        if ("BLOCK".equalsIgnoreCase(browsingHistory.getActionTaken())) {

            Alert alert = new Alert();

            alert.setChild(child);
            alert.setParent(child.getParent());

            alert.setAlertType("BLOCKED_WEBSITE_ATTEMPT");

            alert.setMessage(
                    "Blocked website attempt: "
                            + browsingHistory.getUrl()
            );

            alert.setRead(false);

            alertService.saveAlert(alert);
        }
        if ("BLOCK".equalsIgnoreCase(browsingHistory.getActionTaken())) {

            LocalDateTime tenMinutesAgo =
                    LocalDateTime.now().minusMinutes(10);

            long blockedAttempts =
                    browsinghistoryrepository.countBlockedAttempts(
                            child.getChildId(),
                            tenMinutesAgo
                    );

            if (blockedAttempts >= 5) {

                boolean suspiciousAlertExists =
                        alertService.unreadAlertExists(
                                child.getChildId(),
                                "SUSPICIOUS_ACTIVITY"
                        );

                if (!suspiciousAlertExists) {

                    Alert alert = new Alert();

                    alert.setChild(child);
                    alert.setParent(child.getParent());

                    alert.setAlertType("SUSPICIOUS_ACTIVITY");

                    alert.setMessage(
                            child.getName()
                                    + " repeatedly attempted to access blocked websites. "
                                    + blockedAttempts
                                    + " blocked attempts detected within 10 minutes."
                    );

                    alert.setRead(false);

                    alertService.saveAlert(alert);
                }
            }
        }
        if ("HIGH".equals(riskLevel)) {

            boolean unreadAlertExists =
                    alertService.unreadAlertExists(
                            child.getChildId(),
                            "HIGH_RISK_WEBSITE"
                    );

            if (!unreadAlertExists) {

                Alert alert = new Alert();

                alert.setChild(child);
                alert.setParent(child.getParent());

                alert.setAlertType("HIGH_RISK_WEBSITE");

                alert.setMessage(
                        "High-risk website detected: "
                                + browsingHistory.getUrl()
                );

                alert.setRead(false);

                alertService.saveAlert(alert);
            }
        }

        return savedHistory;
    }

    public List<BrowsingHistory> getBrowsingHistoryByChildId(
            Long childId) {

        return browsinghistoryrepository
                .findByChildChildId(childId);
    }
}