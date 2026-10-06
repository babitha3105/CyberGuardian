package com.example.cyberguardian.controller;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.service.AlertService;
import org.springframework.web.bind.annotation.*;
import com.example.cyberguardian.dto.AlertResponseDTO;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final AlertService alertservice;
    public AlertController(AlertService alertservice) {
        this.alertservice = alertservice;
    }
    @PostMapping
    public Alert saveAlert(@RequestBody Alert alert) {
        return alertservice.saveAlert(alert);
    }
    @GetMapping("/parent/{parentId}")
    public List<AlertResponseDTO> getAlertsByParentId(
            @PathVariable Long parentId) {

        List<Alert> alerts =
                alertservice.getAlertsByParentId(parentId);

        return alerts.stream()
                .map(alert -> new AlertResponseDTO(
                        alert.getAlertId(),
                        alert.getAlertType(),
                        alert.getMessage(),
                        alert.isRead(),
                        alert.getCreatedAt(),
                        alert.getChild().getChildId(),
                        alert.getChild().getName()
                ))
                .toList();
    }
    @PutMapping("/{alertId}/read")
    public Alert markAlertAsRead(@PathVariable Long alertId) {
        return alertservice.markAlertAsRead(alertId);
    }
    @GetMapping("/child/{childId}")
    public List<AlertResponseDTO> getAlertsByChildId(
            @PathVariable Long childId) {

        List<Alert> alerts =
                alertservice.getAlertsByChildId(childId);

        return alerts.stream()
                .map(alert -> new AlertResponseDTO(
                        alert.getAlertId(),
                        alert.getAlertType(),
                        alert.getMessage(),
                        alert.isRead(),
                        alert.getCreatedAt(),
                        alert.getChild().getChildId(),
                        alert.getChild().getName()
                ))
                .toList();
    }
}
