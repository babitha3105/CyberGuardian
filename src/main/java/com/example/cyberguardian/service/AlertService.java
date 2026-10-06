package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.repository.AlertRepository;
import org.springframework.stereotype.Service;
import com.example.cyberguardian.entity.ParentFcmToken;
import com.example.cyberguardian.repository.ParentFcmTokenRepository;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final ParentFcmTokenRepository parentFcmTokenRepository;
    private final FcmNotificationService fcmNotificationService;
    public AlertService(AlertRepository alertRepository, ParentFcmTokenRepository parentFcmTokenRepository, FcmNotificationService fcmNotificationService) {
        this.alertRepository = alertRepository;
        this.parentFcmTokenRepository = parentFcmTokenRepository;
        this.fcmNotificationService = fcmNotificationService;
    }

    public Alert saveAlert(Alert alert) {

        Alert savedAlert = alertRepository.save(alert);

        Long parentId = alert.getParent().getParentId();

        parentFcmTokenRepository
                .findByParentParentId(parentId)
                .ifPresent(token -> {

                    fcmNotificationService.sendNotification(
                            token.getFcmToken(),
                            "CyberGuardian Alert",
                            alert.getMessage()
                    );

                });

        return savedAlert;
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
    public List<Alert> getAlertsByChildId(Long childId) {
        return alertRepository.findByChildChildId(childId);
    }
}