package com.example.cyberguardian.dto;

import java.time.LocalDateTime;

public class PolicyResponseDTO {

    private Long policyId;
    private String targetType;
    private String targetValue;
    private String action;
    private LocalDateTime createdAt;
    private Long childId;

    public PolicyResponseDTO(
            Long policyId,
            String targetType,
            String targetValue,
            String action,
            LocalDateTime createdAt,
            Long childId) {

        this.policyId = policyId;
        this.targetType = targetType;
        this.targetValue = targetValue;
        this.action = action;
        this.createdAt = createdAt;
        this.childId = childId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getChildId() {
        return childId;
    }
}