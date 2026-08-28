package com.example.cyberguardian.controller;
import com.example.cyberguardian.entity.AccessRequest;
import com.example.cyberguardian.service.AccessRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access-requests")
public class AccessRequestController {
    private final AccessRequestService accessrequestservice;

    public AccessRequestController(AccessRequestService accessrequestservice) {
        this.accessrequestservice = accessrequestservice;
    }
    @PostMapping
    public AccessRequest saveAccessRequest(@RequestBody AccessRequest accessrequest) {
        return accessrequestservice.saveAccessRequest(accessrequest);
    }
    @GetMapping("/child/{childId}")
    public List<AccessRequest> getAccessRequestsByChildId(@PathVariable Long childId) {
        return accessrequestservice.getAccessRequestsByChildId(childId);
    }
    @PutMapping("/{requestId}/respond")
    public AccessRequest respondToAccessRequest(
            @PathVariable Long requestId,
            @RequestParam String status) {

        return accessrequestservice.respondToAccessRequest(requestId, status);
    }
}
