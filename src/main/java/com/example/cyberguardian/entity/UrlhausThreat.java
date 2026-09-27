package com.example.cyberguardian.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urlhaus_threats")
public class UrlhausThreat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threat_id")
    private Long threatId;

    @Column(name = "url", nullable = false, length = 1000)
    private String url;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getThreatId() {
        return threatId;
    }

    public void setThreatId(Long threatId) {
        this.threatId = threatId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}