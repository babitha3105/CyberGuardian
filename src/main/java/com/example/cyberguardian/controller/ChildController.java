package com.example.cyberguardian.controller;
//   eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZWN1cml0eXRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzkxMjA2NzU2LCJleHAiOjE3OTEyMTAzNTZ9.h5cCogiHNnbB7soP-VQ5xDMk1i0fUQnl4GG5alIgPnE
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.service.ChildService;
import com.example.cyberguardian.service.ParentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.List;
@RestController
@RequestMapping("/api/children")
public class ChildController {

    private final ChildService childservice;
    private final ParentService parentService;

    public ChildController(
            ChildService childservice,
            ParentService parentService) {

        this.childservice = childservice;
        this.parentService = parentService;
    }

    @PostMapping
    public Child createChild(@RequestBody Child child) {
        return childservice.saveChild(child);
    }

    @GetMapping("/{childId}")
    public ResponseEntity<?> getChildById(
            @PathVariable Long childId,
            Authentication authentication) {

        String email = authentication.getName();

        Optional<Parent> parent =
                parentService.getParentByEmail(email);

        if (parent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Parent not found");
        }

        Optional<Child> child =
                childservice.getChildById(childId);

        if (child.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Child not found");
        }

        if (!child.get().getParent().getParentId()
                .equals(parent.get().getParentId())) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to access this child");
        }

        return ResponseEntity.ok(child.get());
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<?> getChildrenByParentId(
            @PathVariable Long parentId,
            Authentication authentication) {

        String email = authentication.getName();

        Optional<Parent> parent =
                parentService.getParentByEmail(email);

        if (parent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Parent not found");
        }

        if (!parent.get().getParentId().equals(parentId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to access these children");
        }

        return ResponseEntity.ok(
                childservice.getChildrenByParentId(parentId)
        );
    }
}