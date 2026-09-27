package com.example.cyberguardian.service;
import com.example.cyberguardian.repository.WebsiteListRepository;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.example.cyberguardian.entity.WebsiteList;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import com.example.cyberguardian.entity.UrlhausThreat;
import com.example.cyberguardian.repository.UrlhausThreatRepository;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.boot.CommandLineRunner;
@Service
public class WebsiteListImporterService/* implements CommandLineRunner */{
    private final WebsiteListRepository websiteListRepository;
    private final UrlhausThreatRepository urlhausThreatRepository;
    @Value("${urlhaus.auth-key}")
    private String urlhausAuthKey;
    private static final String ADULT_LIST_URL =
            "https://raw.githubusercontent.com/StevenBlack/hosts/master/alternates/porn-only/hosts";
    private static final String GAMBLING_LIST_URL =
            "https://raw.githubusercontent.com/StevenBlack/hosts/master/alternates/gambling-only/hosts";
    private static final String URLHAUS_RECENT_URLS =
            "https://urlhaus.abuse.ch/downloads/text_recent/";
    public WebsiteListImporterService(
            WebsiteListRepository websiteListRepository, UrlhausThreatRepository urlhausThreatRepository) {

        this.websiteListRepository = websiteListRepository;
        this.urlhausThreatRepository = urlhausThreatRepository;
    }
    public void downloadAdultList() {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ADULT_LIST_URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println("Adult list status: "
                    + response.statusCode());

            if (response.statusCode() != 200) {
                System.out.println("Adult list download failed.");
                return;
            }

            System.out.println("Adult list downloaded successfully.");
            importDomains(response.body(), "STEVENBLACK_PORN");
        } catch (Exception e) {

            System.out.println("Failed to download adult list.");
            e.printStackTrace();
        }
    }
    public void downloadGamblingList() {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GAMBLING_LIST_URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println("Gambling list status: "
                    + response.statusCode());

            if (response.statusCode() != 200) {
                System.out.println("Gambling list download failed.");
                return;
            }

            System.out.println("Gambling list downloaded successfully.");

            importDomains(response.body(), "STEVENBLACK_GAMBLING");

        } catch (Exception e) {

            System.out.println("Failed to download gambling list.");
            e.printStackTrace();
        }
    }
    private void importDomains(String content, String source) {

        Set<String> existingDomains = new HashSet<>();
        List<WebsiteList> newWebsites = new ArrayList<>();

        int newCount = 0;
        int skippedCount = 0;

        for (WebsiteList websiteList : websiteListRepository.findAll()) {

            if ("BLACKLIST".equals(websiteList.getListType())) {
                existingDomains.add(websiteList.getDomain());
            }
        }

        String[] lines = content.split("\\R");

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            String[] parts = line.split("\\s+");

            if (parts.length >= 2) {

                String domain = parts[1];

                if (!domain.contains(".")) {
                    continue;
                }

                if (!existingDomains.contains(domain)) {

                    existingDomains.add(domain);
                    newCount++;

                    WebsiteList websiteList = new WebsiteList();

                    websiteList.setDomain(domain);
                    websiteList.setListType("BLACKLIST");
                    websiteList.setSource(source);

                    newWebsites.add(websiteList);

                } else {

                    skippedCount++;
                }
            }
        }

        System.out.println("Source: " + source);
        System.out.println("New domains: " + newCount);
        System.out.println("Skipped domains: " + skippedCount);

        websiteListRepository.saveAll(newWebsites);
    }
    /*@Override
    public void run(String... args) {

        downloadUrlhausList();

    }*/
    @Scheduled(fixedRate = 21600000)
    public void downloadUrlhausList() {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URLHAUS_RECENT_URLS))
                    .header("Auth-Key", urlhausAuthKey)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "URLhaus status: " + response.statusCode()
            );

            if (response.statusCode() != 200) {
                System.out.println("URLhaus download failed.");
                return;
            }

            System.out.println(
                    "URLhaus list downloaded successfully."
            );

            String[] lines = response.body().split("\\R");

            Set<String> existingUrls = new HashSet<>();
            List<UrlhausThreat> newThreats = new ArrayList<>();

            int newCount = 0;
            int skippedCount = 0;

            // Get existing URLhaus URLs
            for (UrlhausThreat threat : urlhausThreatRepository.findAll()) {

                existingUrls.add(threat.getUrl());

            }

            // Process downloaded URLs
            for (String line : lines) {

                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {

                    URI uri = URI.create(line);

                    if (uri.getHost() == null) {
                        continue;
                    }

                    String exactUrl = line;

                    if (!existingUrls.contains(exactUrl)) {

                        existingUrls.add(exactUrl);
                        newCount++;

                        UrlhausThreat threat = new UrlhausThreat();

                        threat.setUrl(exactUrl);

                        newThreats.add(threat);

                    } else {

                        skippedCount++;
                    }

                } catch (Exception e) {

                    System.out.println(
                            "Invalid URL skipped: " + line
                    );

                }
            }

            System.out.println("Source: URLHAUS");
            System.out.println("New URLs: " + newCount);
            System.out.println("Skipped URLs: " + skippedCount);

            urlhausThreatRepository.saveAll(newThreats);

        } catch (Exception e) {

            System.out.println(
                    "Failed to download URLhaus list."
            );

            e.printStackTrace();
        }
    }
}
