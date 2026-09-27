package com.example.cyberguardian.dto;

import java.time.LocalDateTime;

public class AlertResponseDTO {

    private Long alertId;
    private String alertType;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    private Long childId;
    private String childName;

    public AlertResponseDTO(
            Long alertId,
            String alertType,
            String message,
            boolean read,
            LocalDateTime createdAt,
            Long childId,
            String childName) {

        this.alertId = alertId;
        this.alertType = alertType;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
        this.childId = childId;
        this.childName = childName;
    }

    public Long getAlertId() {
        return alertId;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getChildId() {
        return childId;
    }

    public String getChildName() {
        return childName;
    }
}