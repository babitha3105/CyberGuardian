package com.example.cyberguardian.dto;

public class WebsiteCheckResponse {

    private String decision;
    private String reason;

    public WebsiteCheckResponse(String decision, String reason) {
        this.decision = decision;
        this.reason = reason;
    }

    public String getDecision() {
        return decision;
    }

    public String getReason() {
        return reason;
    }
}