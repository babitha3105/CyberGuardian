package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.AccessRequest;
import com.example.cyberguardian.repository.AccessRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccessRequestService {

    private final AccessRequestRepository accessRequestRepository;

    public AccessRequestService(AccessRequestRepository accessRequestRepository) {
        this.accessRequestRepository = accessRequestRepository;
    }

    public AccessRequest saveAccessRequest(AccessRequest accessRequest) {
        return accessRequestRepository.save(accessRequest);
    }
    public List<AccessRequest> getAccessRequestsByChildId(Long childId) {
        return accessRequestRepository.findByChildChildId(childId);
    }
    public AccessRequest respondToAccessRequest(
            Long requestId,
            String status) {

        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Access request not found"));

        request.setStatus(status);
        request.setRespondedAt(LocalDateTime.now());

        return accessRequestRepository.save(request);
    }
}