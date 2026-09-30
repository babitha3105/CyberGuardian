package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.entity.ParentFcmToken;
import com.example.cyberguardian.repository.ParentFcmTokenRepository;
import com.example.cyberguardian.repository.ParentRepository;
import org.springframework.stereotype.Service;

@Service
public class ParentFcmTokenService {

    private final ParentFcmTokenRepository tokenRepository;
    private final ParentRepository parentRepository;

    public ParentFcmTokenService(
            ParentFcmTokenRepository tokenRepository,
            ParentRepository parentRepository) {

        this.tokenRepository = tokenRepository;
        this.parentRepository = parentRepository;
    }

    public ParentFcmToken saveToken(
            Long parentId,
            String fcmToken) {

        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() ->
                        new RuntimeException("Parent not found"));

        ParentFcmToken token = tokenRepository
                .findByFcmToken(fcmToken)
                .orElse(new ParentFcmToken());

        token.setParent(parent);
        token.setFcmToken(fcmToken);

        return tokenRepository.save(token);
    }
}