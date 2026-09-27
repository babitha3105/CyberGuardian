package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.repository.ChildRepository;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TamperDetectionService {

    private final ChildRepository childRepository;
    private final AlertService alertService;

    public TamperDetectionService(
            ChildRepository childRepository,
            AlertService alertService) {

        this.childRepository = childRepository;
        this.alertService = alertService;
    }

    public boolean isHeartbeatStale(Long childId) {

        Child child = childRepository.findById(childId)
                .orElseThrow(() ->
                        new RuntimeException("Child not found"));

        if (child.getLastSeen() == null) {
            return true;
        }

        LocalDateTime fiveMinutesAgo =
                LocalDateTime.now().minusMinutes(5);

        return child.getLastSeen().isBefore(fiveMinutesAgo);
    }

    @Scheduled(fixedRate = 60000)
    public void checkHeartbeats() {

        System.out.println("Checking heartbeat...");

        List<Child> children = childRepository.findAll();

        for (Child child : children) {

            boolean stale =
                    isHeartbeatStale(child.getChildId());

            if (stale) {

                System.out.println(
                        "Child " + child.getChildId()
                                + " heartbeat is STALE"
                );

                // Check whether an unread tamper alert already exists
                boolean unreadAlertExists =
                        alertService.unreadAlertExists(
                                child.getChildId(),
                                "TAMPER_DETECTED"
                        );

                if (!unreadAlertExists) {

                    Alert alert = new Alert();

                    alert.setChild(child);
                    alert.setParent(child.getParent());

                    alert.setAlertType("TAMPER_DETECTED");

                    alert.setMessage(
                            "CyberGuardian extension may have been stopped or disconnected for Child "
                                    + child.getChildId()
                    );

                    alert.setRead(false);

                    Alert savedAlert =
                            alertService.saveAlert(alert);

                    System.out.println(
                            "Tamper alert created with ID: "
                                    + savedAlert.getAlertId()
                    );

                } else {

                    System.out.println(
                            "Tamper alert already exists for Child "
                                    + child.getChildId()
                    );
                }

            } else {

                System.out.println(
                        "Child " + child.getChildId()
                                + " heartbeat is ACTIVE"
                );
            }
        }
    }
}
