package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.ParentFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentFcmTokenRepository
        extends JpaRepository<ParentFcmToken, Long> {

    Optional<ParentFcmToken> findByFcmToken(String fcmToken);
}