package com.example.cyberguardian.controller;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.service.ChildService;
import  org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
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
    @GetMapping("/{childId}")
    public Optional<Child> getChildById(@PathVariable Long childId) {
        return childservice.getChildById(childId);
    }
    @GetMapping("/parent/{parentId}")
    public List<Child> getChildrenByParentId(
            @PathVariable Long parentId) {

        return childservice.getChildrenByParentId(parentId);
    }
}
