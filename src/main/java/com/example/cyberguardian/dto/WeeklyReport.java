package com.example.cyberguardian.dto;

public class WeeklyReport {

    private int totalWebsites;
    private int totalSearches;
    private int totalAlerts;

    private long blockedWebsites;
    private long highRiskSearches;

    private long educationSearches;
    private long sportsSearches;
    private long entertainmentSearches;
    private long gamingSearches;
    private long otherSearches;
    private String safetyStatus;
    public int getTotalWebsites() {
        return totalWebsites;
    }

    public String getSafetyStatus() {
        return safetyStatus;
    }

    public void setSafetyStatus(String safetyStatus) {
        this.safetyStatus = safetyStatus;
    }

    public void setTotalWebsites(int totalWebsites) {
        this.totalWebsites = totalWebsites;
    }

    public int getTotalSearches() {
        return totalSearches;
    }

    public void setTotalSearches(int totalSearches) {
        this.totalSearches = totalSearches;
    }

    public int getTotalAlerts() {
        return totalAlerts;
    }

    public void setTotalAlerts(int totalAlerts) {
        this.totalAlerts = totalAlerts;
    }

    public long getBlockedWebsites() {
        return blockedWebsites;
    }

    public void setBlockedWebsites(long blockedWebsites) {
        this.blockedWebsites = blockedWebsites;
    }

    public long getHighRiskSearches() {
        return highRiskSearches;
    }

    public void setHighRiskSearches(long highRiskSearches) {
        this.highRiskSearches = highRiskSearches;
    }

    public long getEducationSearches() {
        return educationSearches;
    }

    public void setEducationSearches(long educationSearches) {
        this.educationSearches = educationSearches;
    }

    public long getSportsSearches() {
        return sportsSearches;
    }

    public void setSportsSearches(long sportsSearches) {
        this.sportsSearches = sportsSearches;
    }

    public long getEntertainmentSearches() {
        return entertainmentSearches;
    }

    public void setEntertainmentSearches(long entertainmentSearches) {
        this.entertainmentSearches = entertainmentSearches;
    }

    public long getGamingSearches() {
        return gamingSearches;
    }

    public void setGamingSearches(long gamingSearches) {
        this.gamingSearches = gamingSearches;
    }

    public long getOtherSearches() {
        return otherSearches;
    }

    public void setOtherSearches(long otherSearches) {
        this.otherSearches = otherSearches;
    }
// getters and setters
}