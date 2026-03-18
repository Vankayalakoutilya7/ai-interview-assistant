package com.interview.assistant.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @Column(length = 2000)
    private String modelAnswer;

    @ElementCollection
    private List<String> keywords;
}