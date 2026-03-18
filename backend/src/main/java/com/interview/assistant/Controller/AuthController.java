package com.interview.assistant.Controller;

import com.interview.assistant.Model.User;
import com.interview.assistant.Repository.UserRepository;
import com.interview.assistant.Util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {

        // ✅ generate token after signup
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(user);

        return jwtUtil.generateToken(user.getUsername());
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        System.out.println(user);

        User existing = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!existing.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}