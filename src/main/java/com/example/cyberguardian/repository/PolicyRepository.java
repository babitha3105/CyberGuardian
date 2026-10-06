package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findByChildChildId(Long childId);

    Optional<Policy> findByChildChildIdAndTargetValue(
            Long childId,
            String targetValue
    );
}

