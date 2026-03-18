package com.interview.assistant.Service;

import com.interview.assistant.Client.MLServiceClient;
import com.interview.assistant.Model.*;
import com.interview.assistant.Repository.EvaluationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;

@Service
public class EvaluationService {

    private final QuestionService questionService;

    private final MLServiceClient mlServiceClient;

    private final EvaluationRepository evaluationRepository;

    public EvaluationService(QuestionService questionService,
                             MLServiceClient mlServiceClient,
                             EvaluationRepository evaluationRepository) {
        this.questionService = questionService;
        this.mlServiceClient = mlServiceClient;
        this.evaluationRepository = evaluationRepository;
    }

    public EvaluationResponse evaluate(EvaluationRequest request, String username) {

        Question q = questionService.getById(request.getQuestionId());

        String userAnswer = request.getUserAnswer().toLowerCase();

        // 🔹 Keyword Score
        List<String> keywords = q.getKeywords();
        long matched = keywords.stream()
                .filter(k -> userAnswer.contains(k.toLowerCase()))
                .count();

        double keywordScore = (matched * 100.0) / keywords.size();

        // 🔹 Dummy similarity (we replace later with ML)
        double similarityScore = mlServiceClient.getSimilarityScore(
                request.getUserAnswer(),
                q.getModelAnswer()
        );

        // 🔹 Final score
        double finalScore =
                0.5 * similarityScore +
                        0.3 * keywordScore +
                        0.2 * Math.min(request.getUserAnswer().length() / 10.0, 100);

        // 🔹 Missing keywords
        List<String> missing = keywords.stream()
                .filter(k -> !userAnswer.contains(k.toLowerCase()))
                .toList();

        // 🔹 Response
        EvaluationResponse res = new EvaluationResponse();
        res.setScore(finalScore);
        res.setKeywordScore(keywordScore);
        res.setSimilarityScore(similarityScore);
        res.setMissingKeywords(missing);
        res.setModelAnswer(q.getModelAnswer());

        String feedback;

        if (finalScore > 80) {
            feedback = "Excellent answer!";
        } else if (finalScore > 60) {
            feedback = "Good answer, but can be improved.";
        } else {
            feedback = "Answer is weak, revise concepts.";
        }

        res.setFeedback(feedback + " Missing: " + missing);

        Evaluation eval = new Evaluation();
        eval.setQuestionId(q.getId());
        eval.setUserAnswer(request.getUserAnswer());
        eval.setScore(finalScore);
        eval.setSimilarityScore(similarityScore);
        eval.setKeywordScore(keywordScore);
        eval.setUsername(username);
        evaluationRepository.save(eval);
        return res;
    }
}