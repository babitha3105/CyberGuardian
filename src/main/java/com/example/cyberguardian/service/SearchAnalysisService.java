        package com.example.cyberguardian.service;

import org.springframework.stereotype.Service;

@Service
public class SearchAnalysisService {

    public String determineCategory(String searchQuery) {

        String query = searchQuery.toLowerCase();

        if (query.contains("java") ||
                query.contains("spring") ||
                query.contains("programming") ||
                query.contains("tutorial")) {

            return "EDUCATION";
        }

        if (query.contains("football") ||
                query.contains("cricket") ||
                query.contains("sports")) {

            return "SPORTS";
        }

        if (query.contains("movie") ||
                query.contains("music") ||
                query.contains("songs")) {

            return "ENTERTAINMENT";
        }

        if (query.contains("game") ||
                query.contains("gaming")) {

            return "GAMING";
        }

        return "OTHER";
    }

    public String determineRiskLevel(String searchQuery) {

        String query = searchQuery.toLowerCase().trim();

        int score = 0;

        if (query.contains("how to hack") ||
                query.contains("hack someone's") ||
                query.contains("hack an account") ||
                query.contains("steal password")) {

            score += 4;
        }

        if (query.contains("download hacking tool") ||
                query.contains("password cracker") ||
                query.contains("hacking tool")) {

            score += 4;
        }

        if (query.contains("make a bomb") ||
                query.contains("make explosive") ||
                query.contains("buy weapon")) {

            score += 4;
        }

        if (query.contains("porn") ||
                query.contains("xxx")) {

            score += 4;
        }

        // HIGH risk if the query matches at least one high-risk rule
        if (score >= 4) {
            return "HIGH";
        }

        return "LOW";
    }
}
