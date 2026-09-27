package com.example.cyberguardian.dto;

import java.time.LocalDateTime;

public class AccessRequestResponseDTO {

    private Long requestId;
    private String url;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime respondedAt;

    private Long childId;
    private String childName;

    public AccessRequestResponseDTO(
            Long requestId,
            String url,
            String status,
            LocalDateTime requestedAt,
            LocalDateTime respondedAt,
            Long childId,
            String childName) {

        this.requestId = requestId;
        this.url = url;
        this.status = status;
        this.requestedAt = requestedAt;
        this.respondedAt = respondedAt;
        this.childId = childId;
        this.childName = childName;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public Long getChildId() {
        return childId;
    }

    public String getChildName() {
        return childName;
    }
}