package com.interview.assistant.Model;

import lombok.Data;

@Data
public class EvaluationRequest {
    private Long questionId;
    private String userAnswer;
}