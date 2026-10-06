package com.example.cyberguardian.service;

import com.example.cyberguardian.dto.GeminiSearchAnalysisDTO;
import com.example.cyberguardian.entity.SearchHistory;
import com.google.gson.Gson;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.HttpOptions;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import org.springframework.stereotype.Service;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.repository.ChildRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GeminiSearchAnalysisService {

    private final AlertService alertService;
    private final ChildRepository childRepository;

    public GeminiSearchAnalysisService(
            AlertService alertService,
            ChildRepository childRepository) {

        this.alertService = alertService;
        this.childRepository = childRepository;
    }

    public GeminiSearchAnalysisDTO analyzeSearches(
            List<String> searches) {

        if (searches == null || searches.isEmpty()) {

            GeminiSearchAnalysisDTO result =
                    new GeminiSearchAnalysisDTO();

            result.setCategories(List.of("OTHER"));
            result.setIntent("UNCLEAR");
            result.setPattern("UNCLEAR");
            result.setRiskLevel("LOW");
            result.setExplanation(
                    "No searches available for analysis."
            );

            return result;
        }

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are analyzing a child's recent web search activity
                for a parental safety system.

                Analyze the searches as a GROUP, not individually.

                Identify:
                1. Overall categories (one or more)
                2. Apparent overall intent
                3. Overall search pattern
                4. Overall risk level
                5. Short explanation of the group

                Categories:
                EDUCATION, SPORTS, ENTERTAINMENT, GAMING,
                SOCIAL_MEDIA, SHOPPING, CYBERSECURITY, HEALTH, OTHER

                Return one or more categories when the searches cover
                different topics.

                If the searches clearly belong to different unrelated
                categories, include all relevant categories rather than
                choosing only one.

                Allowed intents:
                LEARNING, RESEARCH, ENTERTAINMENT, SHOPPING, SOCIAL,
                TROUBLESHOOTING, INFORMATION_SEEKING,
                POTENTIALLY_HARMFUL, UNCLEAR, MIXED

                Allowed patterns:
                NORMAL, REPEATED, EXPLORATORY, ESCALATING,
                SUDDEN_CHANGE, MIXED, UNCLEAR

                Risk levels:
                LOW, MEDIUM, HIGH

                Do not make assumptions beyond the searches provided.
                Do not use this analysis to block websites.

                The analysis must represent the GROUP of searches,
                not just the most prominent individual search.

                Return only the requested structured JSON.

                Searches:
                """);

        for (String search : searches) {
            prompt.append("- ").append(search).append("\n");
        }

        Schema categoryItemSchema =
                Schema.builder()
                        .type(Type.Known.STRING)
                        .build();

        Schema schema =
                Schema.builder()
                        .type(Type.Known.OBJECT)
                        .properties(
                                Map.of(

                                        "categories",
                                        Schema.builder()
                                                .type(Type.Known.ARRAY)
                                                .items(categoryItemSchema)
                                                .build(),

                                        "intent",
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build(),

                                        "pattern",
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build(),

                                        "riskLevel",
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build(),

                                        "explanation",
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build()
                                )
                        )
                        .required(Arrays.asList(
                                "categories",
                                "intent",
                                "pattern",
                                "riskLevel",
                                "explanation"
                        ))
                        .build();

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .responseSchema(schema)
                        .build();

        HttpOptions httpOptions =
                HttpOptions.builder()
                        .timeout(120000)
                        .build();

        try (Client client = Client.builder()
                .httpOptions(httpOptions)
                .build()) {

            System.out.println("GEMINI CLIENT CREATED");
            System.out.println("GEMINI API CALL STARTING");

            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-3.7-flash",
                            prompt.toString(),
                            config
                    );

            System.out.println("GEMINI API CALL RETURNED");
            System.out.println(
                    "GEMINI RESPONSE: " + response.text()
            );

            Gson gson = new Gson();

            return gson.fromJson(
                    response.text(),
                    GeminiSearchAnalysisDTO.class
            );

        } catch (Exception e) {

            System.out.println(
                    "Gemini search analysis failed:"
            );

            e.printStackTrace();

            GeminiSearchAnalysisDTO result =
                    new GeminiSearchAnalysisDTO();

            result.setCategories(List.of("OTHER"));
            result.setIntent("UNCLEAR");
            result.setPattern("UNCLEAR");
            result.setRiskLevel("LOW");
            result.setExplanation(
                    "Gemini analysis failed."
            );

            return result;
        }
    }

    public GeminiSearchAnalysisDTO analyzeRecentSearchHistory(
            Long childId,
            List<SearchHistory> searchHistories) {

        List<String> searches =
                searchHistories.stream()
                        .map(SearchHistory::getSearchQuery)
                        .toList();

        GeminiSearchAnalysisDTO result =
                analyzeSearches(searches);

        if ("HIGH".equals(result.getRiskLevel())) {

            boolean alertExists =
                    alertService.unreadAlertExists(
                            childId,
                            "HIGH_RISK_SEARCH_PATTERN"
                    );

            if (!alertExists) {

                Child child = childRepository.findById(childId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Child not found"
                                ));

                Alert alert = new Alert();

                alert.setChild(child);
                alert.setParent(child.getParent());

                alert.setAlertType(
                        "HIGH_RISK_SEARCH_PATTERN"
                );

                alert.setMessage(
                        "High-risk search pattern detected: "
                                + result.getExplanation()
                );

                alert.setRead(false);

                alertService.saveAlert(alert);
            }
        }

        return result;
    }
}