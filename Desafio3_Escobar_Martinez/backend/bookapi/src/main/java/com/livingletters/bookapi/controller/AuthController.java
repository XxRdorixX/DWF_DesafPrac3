package com.livingletters.bookapi.controller;

import com.livingletters.bookapi.model.AppUser;
import com.livingletters.bookapi.repository.AppUserRepository;
import com.livingletters.bookapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.livingletters.bookapi.dto.AuthResponseDTO;
import java.util.List;
import jakarta.validation.Valid;


/**
 * Handles user authentication (login/register) and JWT issuance.
 * Maneja el registro e inicio de sesión de usuarios con JWT.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@Valid @RequestBody AppUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }


    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AppUser user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        String token = jwtTokenProvider.generateToken(user.getUsername());
        long expiresAt = System.currentTimeMillis() + jwtTokenProvider.getExpirationMs();

        return new AuthResponseDTO(token, expiresAt, List.of("USER"));
    }

}
