package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.Policy;
import com.example.cyberguardian.service.PolicyService;
import com.example.cyberguardian.dto.PolicyResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyservice;

    public PolicyController(PolicyService policyservice) {
        this.policyservice = policyservice;
    }

    @PostMapping
    public PolicyResponseDTO createPolicy(
            @RequestBody Policy policy) {

        Policy savedPolicy =
                policyservice.savePolicy(policy);

        return convertToDTO(savedPolicy);
    }

    @GetMapping("/child/{childId}")
    public List<PolicyResponseDTO> getPoliciesByChildId(
            @PathVariable Long childId) {

        List<Policy> policies =
                policyservice.getPoliciesByChildId(childId);

        return policies.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @PutMapping("/{policyId}")
    public PolicyResponseDTO updatePolicy(
            @PathVariable Long policyId,
            @RequestBody Policy updatedPolicy) {

        Policy updated =
                policyservice.updatePolicy(
                        policyId,
                        updatedPolicy
                );

        return convertToDTO(updated);
    }

    @DeleteMapping("/{policyId}")
    public void deletePolicy(@PathVariable Long policyId) {
        policyservice.deletePolicy(policyId);
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