
        package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Policy;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.repository.PolicyRepository;
import com.example.cyberguardian.repository.ChildRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    private final PolicyRepository policyrepository;
    private final ChildRepository childRepository;

    public PolicyService(
            PolicyRepository policyrepository,
            ChildRepository childRepository) {

        this.policyrepository = policyrepository;
        this.childRepository = childRepository;
    }

    public Policy savePolicy(Policy policy) {

        return policyrepository.save(policy);
    }

    public List<Policy> getPoliciesByChildId(Long childId) {

        return policyrepository.findByChildChildId(childId);
    }

    public Policy updatePolicy(
            Long policyId,
            Policy updatedPolicy) {

        Policy existingPolicy =
                policyrepository.findById(policyId)
                        .orElseThrow(() ->
                                new RuntimeException("Policy not found"));

        existingPolicy.setTargetType(
                updatedPolicy.getTargetType());

        existingPolicy.setTargetValue(
                updatedPolicy.getTargetValue());

        existingPolicy.setAction(
                updatedPolicy.getAction());

        return policyrepository.save(existingPolicy);
    }

    public void deletePolicy(Long policyId) {

        policyrepository.deleteById(policyId);
    }

    public boolean childBelongsToParent(
            Long childId,
            Long parentId) {

        Optional<Child> child =
                childRepository.findById(childId);

        if (child.isEmpty()) {
            return false;
        }

        return child.get()
                .getParent()
                .getParentId()
                .equals(parentId);
    }
    public Optional<Policy> getPolicyById(Long policyId) {

        return policyrepository.findById(policyId);
    }
}

