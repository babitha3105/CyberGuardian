package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.service.ParentService;
import org.springframework.web.bind.annotation.*;

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
}