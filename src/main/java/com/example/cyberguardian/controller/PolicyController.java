package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.Policy;
import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.service.PolicyService;
import com.example.cyberguardian.service.ParentService;
import com.example.cyberguardian.dto.PolicyResponseDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyservice;
    private final ParentService parentService;

    public PolicyController(
            PolicyService policyservice,
            ParentService parentService) {

        this.policyservice = policyservice;
        this.parentService = parentService;
    }

    @PostMapping
    public ResponseEntity<?> createPolicy(
            @RequestBody Policy policy,
            Authentication authentication) {

        String email = authentication.getName();

        Optional<Parent> parent =
                parentService.getParentByEmail(email);

        if (parent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Parent not found");
        }

        Long childId =
                policy.getChild().getChildId();

        boolean belongs =
                policyservice.childBelongsToParent(
                        childId,
                        parent.get().getParentId());

        if (!belongs) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to create a policy for this child");
        }

        Policy savedPolicy =
                policyservice.savePolicy(policy);

        return ResponseEntity.ok(
                convertToDTO(savedPolicy)
        );
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<?> getPoliciesByChildId(
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

        boolean belongs =
                policyservice.childBelongsToParent(
                        childId,
                        parent.get().getParentId());

        if (!belongs) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to access these policies");
        }

        List<Policy> policies =
                policyservice.getPoliciesByChildId(childId);

        List<PolicyResponseDTO> response =
                policies.stream()
                        .map(this::convertToDTO)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{policyId}")
    public ResponseEntity<?> updatePolicy(
            @PathVariable Long policyId,
            @RequestBody Policy updatedPolicy,
            Authentication authentication) {

        String email = authentication.getName();

        Optional<Parent> parent =
                parentService.getParentByEmail(email);

        if (parent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Parent not found");
        }

        Optional<Policy> existingPolicy =
                policyservice.getPolicyById(policyId);

        if (existingPolicy.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Policy not found");
        }

        Long childId =
                existingPolicy.get()
                        .getChild()
                        .getChildId();

        boolean belongs =
                policyservice.childBelongsToParent(
                        childId,
                        parent.get().getParentId());

        if (!belongs) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to update this policy");
        }

        Policy updated =
                policyservice.updatePolicy(
                        policyId,
                        updatedPolicy
                );

        return ResponseEntity.ok(
                convertToDTO(updated)
        );
    }
    @DeleteMapping("/{policyId}")
    public ResponseEntity<?> deletePolicy(
            @PathVariable Long policyId,
            Authentication authentication) {

        String email = authentication.getName();

        Optional<Parent> parent =
                parentService.getParentByEmail(email);

        if (parent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Parent not found");
        }

        Optional<Policy> existingPolicy =
                policyservice.getPolicyById(policyId);

        if (existingPolicy.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Policy not found");
        }

        Long childId =
                existingPolicy.get()
                        .getChild()
                        .getChildId();

        boolean belongs =
                policyservice.childBelongsToParent(
                        childId,
                        parent.get().getParentId());

        if (!belongs) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to delete this policy");
        }

        policyservice.deletePolicy(policyId);

        return ResponseEntity.ok(
                "Policy deleted successfully"
        );
    }

    private PolicyResponseDTO convertToDTO(Policy policy) {

        return new PolicyResponseDTO(
                policy.getPolicyId(),
                policy.getTargetType(),
                policy.getTargetValue(),
                policy.getAction(),
                policy.getCreatedAt(),
                policy.getChild().getChildId()
        );
    }
}

