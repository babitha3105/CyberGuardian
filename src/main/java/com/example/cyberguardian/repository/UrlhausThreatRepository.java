package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.UrlhausThreat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlhausThreatRepository
        extends JpaRepository<UrlhausThreat, Long> {

    Optional<UrlhausThreat> findByUrl(String url);
}