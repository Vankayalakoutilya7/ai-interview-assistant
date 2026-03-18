package com.interview.assistant.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class MLServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public double getSimilarityScore(String userAnswer, String modelAnswer) {

        String url = "http://localhost:5000/evaluate";

        Map<String, String> request = Map.of(
                "userAnswer", userAnswer,
                "modelAnswer", modelAnswer
        );

        Map response = restTemplate.postForObject(url, request, Map.class);

        return Double.parseDouble(response.get("similarityScore").toString());
    }
}