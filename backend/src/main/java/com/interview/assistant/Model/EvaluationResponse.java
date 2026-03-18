package com.interview.assistant.Model;

import lombok.Data;
import java.util.List;

@Data
public class EvaluationResponse {
    private double score;
    private double similarityScore;
    private double keywordScore;
    private List<String> missingKeywords;
    private String feedback;
    private String modelAnswer;
}