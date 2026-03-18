package com.interview.assistant.Service;

import com.interview.assistant.Model.Question;
import jakarta.annotation.PostConstruct;
import com.interview.assistant.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void init() {
        if (questionRepository.count() == 0) {

            Question q1 = new Question();
            q1.setQuestionText("What is Java?");
            q1.setModelAnswer("Java is an object-oriented programming language.");
            q1.setKeywords(List.of("OOP", "JVM", "Platform Independent"));

            Question q2 = new Question();
            q2.setQuestionText("What is OOP?");
            q2.setModelAnswer("OOP is based on objects, inheritance, encapsulation.");
            q2.setKeywords(List.of("Encapsulation", "Inheritance", "Polymorphism"));

            questionRepository.saveAll(List.of(q1, q2));
        }
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getById(Long id) {
        return questionRepository.findById(id).orElseThrow();
    }
}