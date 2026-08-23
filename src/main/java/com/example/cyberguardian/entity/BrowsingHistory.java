package com.example.cyberguardian.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="browsing_history")
public class BrowsingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;
    @Column(name = "url", nullable = false)
    private String url;

    private String category;

    @Column(name = "visited_at", insertable = false, updatable = false)
    private LocalDateTime visitedAt;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "action_taken")
    private String actionTaken;

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getVisitedAt() {
        return visitedAt;
    }

    public void setVisitedAt(LocalDateTime visitedAt) {
        this.visitedAt = visitedAt;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }
}
