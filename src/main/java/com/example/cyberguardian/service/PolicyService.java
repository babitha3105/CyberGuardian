package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.Policy;
import com.example.cyberguardian.repository.PolicyRepository;
import org.springframework.stereotype.Service;
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
}
