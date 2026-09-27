package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.OpenPhishThreat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenPhishThreatRepository
        extends JpaRepository<OpenPhishThreat, Long> {

    Optional<OpenPhishThreat> findByUrl(String url);
}