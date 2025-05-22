package com.akash.movieexplorer.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.akash.movieexplorer.dto.AuthResponse;
import com.akash.movieexplorer.dto.LoginRequest;
import com.akash.movieexplorer.dto.RegisterRequest;
import com.akash.movieexplorer.entity.User;
import com.akash.movieexplorer.repository.UserRepository;
import com.akash.movieexplorer.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Username or Email already exists");
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRoles(Set.of("USER"));

    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getUsername());
    return new AuthResponse(token);
}

public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

    String token = jwtUtil.generateToken(user.getUsername());
    return new AuthResponse(token);
}
}
