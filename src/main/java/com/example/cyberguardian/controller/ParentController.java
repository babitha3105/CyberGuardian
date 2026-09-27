package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.service.ParentService;
import org.springframework.web.bind.annotation.*;
import com.example.cyberguardian.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }
    @PostMapping("/register")
    public Parent createParent(@RequestBody Parent parent) {
        return parentService.saveParent(parent);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String token = parentService.login(request);

        if (token != null) {
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
    }
}