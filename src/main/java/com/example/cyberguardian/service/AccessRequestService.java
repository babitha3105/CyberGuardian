package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.AccessRequest;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.repository.AccessRequestRepository;
import org.springframework.stereotype.Service;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.repository.ChildRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccessRequestService {

    private final AccessRequestRepository accessRequestRepository;
    private final AlertService alertService;
    private final ChildRepository childRepository;
    public AccessRequestService(
            AccessRequestRepository accessRequestRepository,
            AlertService alertService, ChildRepository childRepository) {

        this.accessRequestRepository = accessRequestRepository;
        this.alertService = alertService;
        this.childRepository = childRepository;
    }

    public AccessRequest saveAccessRequest(AccessRequest accessRequest) {

        Child child = childRepository.findById(
                accessRequest.getChild().getChildId()
        ).orElseThrow(() ->
                new RuntimeException("Child not found"));

        accessRequest.setChild(child);

        Optional<AccessRequest> existingRequest =
                accessRequestRepository
                        .findByChildChildIdAndUrlAndStatus(
                                child.getChildId(),
                                accessRequest.getUrl(),
                                "PENDING"
                        );

        if (existingRequest.isPresent()) {
            return existingRequest.get();
        }

        AccessRequest savedRequest =
                accessRequestRepository.save(accessRequest);

        Alert alert = new Alert();

        alert.setChild(child);
        alert.setParent(child.getParent());
        alert.setAlertType("ACCESS_REQUEST");
        alert.setMessage(
                "Access request received for: " + accessRequest.getUrl()
        );
        alert.setRead(false);

        alertService.saveAlert(alert);

        return savedRequest;
    }
    public List<AccessRequest> getAccessRequestsByChildId(Long childId) {
        return accessRequestRepository.findByChildChildId(childId);
    }

    public AccessRequest respondToAccessRequest(
            Long requestId,
            String status) {

        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new RuntimeException("Access request not found"));

        request.setStatus(status);
        request.setRespondedAt(LocalDateTime.now());

        return accessRequestRepository.save(request);
    }
}