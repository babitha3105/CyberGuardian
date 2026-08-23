package com.example.cyberguardian.controller;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.service.ChildService;
import  org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/children")
public class ChildController {
    private final ChildService childservice;

    public ChildController(ChildService childservice) {
        this.childservice = childservice;
    }
    @PostMapping
    public Child createChild(@RequestBody Child child) {
        return childservice.saveChild(child);
    }
}
