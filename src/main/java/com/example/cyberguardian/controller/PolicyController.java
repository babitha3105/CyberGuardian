package com.example.cyberguardian.controller;
import com.example.cyberguardian.entity.Policy;
import com.example.cyberguardian.service.PolicyService;
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
    public Policy createPolicy(@RequestBody Policy policy) {
        return policyservice.savePolicy(policy);
    }
    @GetMapping("/child/{childId}")
    public List<Policy> getPoliciesByChildId(@PathVariable Long childId) {
        return policyservice.getPoliciesByChildId(childId);
    }
    @PutMapping("/{policyId}")
    public Policy updatePolicy(
            @PathVariable Long policyId,
            @RequestBody Policy updatedPolicy) {

        return policyservice.updatePolicy(policyId, updatedPolicy);
    }
    @DeleteMapping("/{policyId}")
    public void deletePolicy(@PathVariable Long policyId) {
        policyservice.deletePolicy(policyId);
    }
}