package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.OpenPhishThreat;
import com.example.cyberguardian.repository.OpenPhishThreatRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import org.springframework.boot.CommandLineRunner;
@Service
public class OpenPhishImporterService/* implements CommandLineRunner */{

    private final OpenPhishThreatRepository openPhishThreatRepository;

    @Value("${openphish.feed-url}")
    private String openPhishFeedUrl;

    public OpenPhishImporterService(
            OpenPhishThreatRepository openPhishThreatRepository) {

        this.openPhishThreatRepository = openPhishThreatRepository;
    }
    @Scheduled(fixedRate = 21600000)
    public void downloadOpenPhishList() {

        try {


            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(openPhishFeedUrl))
                    .GET()
                    .build();


            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "OpenPhish status: " + response.statusCode()
            );

            if (response.statusCode() != 200) {
                System.out.println("OpenPhish download failed.");
                return;
            }

            System.out.println(
                    "OpenPhish list downloaded successfully."
            );

            String[] lines = response.body().split("\\R");

            Set<String> existingUrls = new HashSet<>();
            List<OpenPhishThreat> newThreats = new ArrayList<>();

            int newCount = 0;
            int skippedCount = 0;

            // Get existing OpenPhish URLs
            for (OpenPhishThreat threat :
                    openPhishThreatRepository.findAll()) {

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

                        OpenPhishThreat threat =
                                new OpenPhishThreat();

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

            System.out.println("Source: OPENPHISH");
            System.out.println("New URLs: " + newCount);
            System.out.println("Skipped URLs: " + skippedCount);

            openPhishThreatRepository.saveAll(newThreats);

        } catch (Exception e) {

            System.out.println(
                    "Failed to download OpenPhish list."
            );

            e.printStackTrace();
        }
    }
    /*@Override
    public void run(String... args) {
        System.out.println("OPENPHISH RUNNER STARTED");

        downloadOpenPhishList();
    }*/
}