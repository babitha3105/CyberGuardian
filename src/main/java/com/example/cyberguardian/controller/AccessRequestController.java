package com.example.cyberguardian.controller;
import com.example.cyberguardian.entity.AccessRequest;
import com.example.cyberguardian.service.AccessRequestService;
import com.example.cyberguardian.dto.AccessRequestResponseDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/access-requests")
public class AccessRequestController {

    private final AccessRequestService accessrequestservice;

    public AccessRequestController(
            AccessRequestService accessrequestservice
             ) {

        this.accessrequestservice = accessrequestservice;
    }

    @PostMapping
    public AccessRequest saveAccessRequest(
            @RequestBody AccessRequest accessrequest) {

        return accessrequestservice
                .saveAccessRequest(accessrequest);
    }


    @GetMapping("/child/{childId}")
    public ResponseEntity<?> getAccessRequestsByChildId(
            @PathVariable Long childId) {

        List<AccessRequest> requests =
                accessrequestservice
                        .getAccessRequestsByChildId(childId);

        List<AccessRequestResponseDTO> response =
                requests.stream()
                        .map(request ->
                                new AccessRequestResponseDTO(
                                        request.getRequestId(),
                                        request.getUrl(),
                                        request.getStatus(),
                                        request.getRequestedAt(),
                                        request.getRespondedAt(),
                                        request.getChild().getChildId(),
                                        request.getChild().getName()
                                ))
                        .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{requestId}/respond")
    public AccessRequest respondToAccessRequest(
            @PathVariable Long requestId,
            @RequestParam String status) {

        return accessrequestservice
                .respondToAccessRequest(
                        requestId,
                        status);
    }
}