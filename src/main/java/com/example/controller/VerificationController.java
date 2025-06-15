package com.example.controller;

import com.example.model.VerificationToken;
import com.example.repository.UserRepository;
import com.example.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class VerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "redirect:/login?expired";
        }

        var user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);
        return "redirect:/login?verified";
    }
}
