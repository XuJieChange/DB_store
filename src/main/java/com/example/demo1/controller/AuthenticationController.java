package com.example.demo1.controller;

import com.example.demo1.dao.entity.JwtBlacklist;
import com.example.demo1.dao.response.InMemoryJwtBlacklist;
import com.example.demo1.dao.response.JwtBlacklistReponsitory;
import com.example.demo1.dto.request.SignUpRequest;
import com.example.demo1.dto.request.SigninRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.dao.response.JwtAuthenticationResponse;
import com.example.demo1.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private InMemoryJwtBlacklist inMemoryJwtBlacklist;

    @Autowired
    private JwtBlacklistReponsitory jwtBlacklistReponsitory;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<JwtAuthenticationResponse> logout(HttpServletRequest request) {
        System.out.println("logoutStart");
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(authHeader) && StringUtils.startsWith(authHeader, "Bearer ")) {
            String jwt = authHeader.substring(7);
            JwtBlacklist jwtBlacklist = new JwtBlacklist();
            jwtBlacklist.setToken(jwt);
            jwtBlacklistReponsitory.save(jwtBlacklist);
        }

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setMessage("Successfully logged out");
        return ResponseEntity.ok(response);
    }


}
