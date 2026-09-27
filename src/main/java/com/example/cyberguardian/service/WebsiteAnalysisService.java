package com.example.cyberguardian.service;
import org.springframework.stereotype.Service;
@Service
public class WebsiteAnalysisService {

    public String determineCategory(String url) {

        String website = url.toLowerCase();

        if (website.contains("youtube") ||
                website.contains("netflix") ||
                website.contains("spotify")) {

            return "ENTERTAINMENT";
        }

        if (website.contains("github") ||
                website.contains("stackoverflow") ||
                website.contains("w3schools")) {

            return "EDUCATION";
        }

        if (website.contains("facebook") ||
                website.contains("instagram") ||
                website.contains("twitter") ||
                website.contains("x.com")) {

            return "SOCIAL";
        }

        if (website.contains("amazon") ||
                website.contains("flipkart")) {

            return "SHOPPING";
        }

        if (website.contains("gambling") ||
                website.contains("casino") ||
                website.contains("betting")) {

            return "GAMBLING";
        }

        return "OTHER";
    }

    public String determineRiskLevel(String url)
    {

        String website = url.toLowerCase();
        if (website.contains("malware") ||
                website.contains("phishing") ||
                website.contains("gambling") ||
                website.contains("casino") ||
                website.contains("adult")) {

            return "HIGH";
        }

        return "LOW";
    }
}