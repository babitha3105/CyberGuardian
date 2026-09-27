package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert saveAlert(Alert alert) {
        return alertRepository.save(alert);
    }
    public List<Alert> getAlertsByParentId(Long parentId) {
        return alertRepository.findByParentParentId(parentId);
    }
    public boolean alertExists(Long childId, String alertType) {
        return alertRepository
                .existsByChildChildIdAndAlertType(
                        childId,
                        alertType
                );
    }
    public Alert markAlertAsRead(Long alertId) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setRead(true);

        return alertRepository.save(alert);
    }
    public boolean unreadAlertExists(Long childId, String alertType) {
        return alertRepository
                .existsByChildChildIdAndAlertTypeAndIsReadFalse(
                        childId,
                        alertType
                );
    }
}