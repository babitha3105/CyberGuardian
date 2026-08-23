package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}