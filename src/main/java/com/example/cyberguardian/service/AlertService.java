package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.repository.AlertRepository;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert saveAlert(Alert alert) {
        return alertRepository.save(alert);
    }
}