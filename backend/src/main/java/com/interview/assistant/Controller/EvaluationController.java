package com.interview.assistant.Controller;

import com.interview.assistant.Model.*;
import com.interview.assistant.Repository.EvaluationRepository;
import com.interview.assistant.Service.EvaluationService;
import com.interview.assistant.Util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluate")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final EvaluationRepository evaluationRepository;

    public EvaluationController(EvaluationService evaluationService,
                                EvaluationRepository evaluationRepository) {
        this.evaluationService = evaluationService;
        this.evaluationRepository = evaluationRepository;
    }

    @PostMapping
    public EvaluationResponse evaluate(
            @RequestBody EvaluationRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String username = JwtUtil.extractUsername(token);

        return evaluationService.evaluate(request, username);
    }

    @GetMapping("/history")
    public List<Evaluation> getHistory(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String username = JwtUtil.extractUsername(token);

        return evaluationRepository.findByUsername(username);
    }
    @DeleteMapping("/clear")
    public String clearHistory(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String username = JwtUtil.extractUsername(token);

        evaluationRepository.deleteByUsername(username);

        return "User history cleared";
    }
}