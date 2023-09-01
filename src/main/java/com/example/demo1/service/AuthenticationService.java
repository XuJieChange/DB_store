package com.example.demo1.service;

import com.example.demo1.dto.request.SignUpRequest;
import com.example.demo1.dto.request.SigninRequest;
import com.example.demo1.dao.entity.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo1.dao.response.JwtAuthenticationResponse;
import com.example.demo1.dao.UsersRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = Users.builder().id(request.getId()).name(request.getName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .is_deleted(false).created_at(LocalDateTime.now())
//                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
