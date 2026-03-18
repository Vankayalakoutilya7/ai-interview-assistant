package com.interview.assistant.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionId;
    private String username;

    @Column(length = 2000)
    private String userAnswer;

    private double score;
    private double similarityScore;
    private double keywordScore;
}