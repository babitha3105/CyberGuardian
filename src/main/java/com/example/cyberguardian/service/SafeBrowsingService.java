package com.example.cyberguardian.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SafeBrowsingService {

    @Value("${google.safebrowsing.api-key}")
    private String apiKey;

    private final RestClient restClient;

    public SafeBrowsingService() {
        this.restClient = RestClient.create();
    }
    public boolean isThreat(String url) {

        String requestBody = """
            {
              "client": {
                "clientId": "cyberguardian",
                "clientVersion": "1.0"
              },
              "threatInfo": {
                "threatTypes": [
                  "MALWARE",
                  "SOCIAL_ENGINEERING",
                  "UNWANTED_SOFTWARE",
                  "POTENTIALLY_HARMFUL_APPLICATION"
                ],
                "platformTypes": [
                  "ANY_PLATFORM"
                ],
                "threatEntryTypes": [
                  "URL"
                ],
                "threatEntries": [
                  {
                    "url": "%s"
                  }
                ]
              }
            }
            """.formatted(url);

        String response = restClient.post()
                .uri("https://safebrowsing.googleapis.com/v4/threatMatches:find?key=" + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);
        System.out.println("Google Safe Browsing response: " + response);

        return response != null && !response.trim().equals("{}");    }
    /*
    restClient.post()
       ↓
Create POST request
       ↓
.uri(...)
       ↓
Tell Google where to send it
       ↓
.header(...)
       ↓
Tell Google we're sending JSON
       ↓
.body(requestBody)
       ↓
Attach URL + threat information
       ↓
.retrieve()
       ↓
Send request to Google
       ↓
.body(String.class)
       ↓
Get Google's response as String
       ↓
return response != null && !response.equals("{}")
       ↓
   true / false
   */
}