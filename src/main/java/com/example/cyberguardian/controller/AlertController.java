package com.example.cyberguardian.controller;
import com.example.cyberguardian.entity.Alert;
import com.example.cyberguardian.service.AlertService;
import org.springframework.web.bind.annotation.*;

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
    public List<Alert> getAlertsByParentId(@PathVariable Long parentId) {
        return alertservice.getAlertsByParentId(parentId);
    }
    @PutMapping("/{alertId}/read")
    public Alert markAlertAsRead(@PathVariable Long alertId) {
        return alertservice.markAlertAsRead(alertId);
    }
}
