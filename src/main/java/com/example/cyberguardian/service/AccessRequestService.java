package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.AccessRequest;
import com.example.cyberguardian.repository.AccessRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class AccessRequestService {

    private final AccessRequestRepository accessRequestRepository;

    public AccessRequestService(AccessRequestRepository accessRequestRepository) {
        this.accessRequestRepository = accessRequestRepository;
    }

    public AccessRequest saveAccessRequest(AccessRequest accessRequest) {
        return accessRequestRepository.save(accessRequest);
    }
}