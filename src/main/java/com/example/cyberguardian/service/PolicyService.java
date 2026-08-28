package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.Policy;
import com.example.cyberguardian.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {
    private final PolicyRepository policyrepository;

    public PolicyService(PolicyRepository policyrepository) {
        this.policyrepository = policyrepository;
    }
    public Policy savePolicy(Policy policy)
    {
        return policyrepository.save(policy);
    }
    public List<Policy> getPoliciesByChildId(Long childId) {
        return policyrepository.findByChildChildId(childId);
    }
    public Policy updatePolicy(Long policyId, Policy updatedPolicy) {

        Policy existingPolicy = policyrepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        existingPolicy.setTargetType(updatedPolicy.getTargetType());
        existingPolicy.setTargetValue(updatedPolicy.getTargetValue());
        existingPolicy.setAction(updatedPolicy.getAction());

        return policyrepository.save(existingPolicy);
    }
    public void deletePolicy(Long policyId) {
        policyrepository.deleteById(policyId);
    }
}
