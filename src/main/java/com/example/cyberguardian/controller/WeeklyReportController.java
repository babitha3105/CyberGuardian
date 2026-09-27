package com.example.cyberguardian.controller;

import com.example.cyberguardian.dto.WeeklyReport;
import com.example.cyberguardian.service.WeeklyReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weekly-report")
public class WeeklyReportController {

    private final WeeklyReportService weeklyreportservice;

    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyreportservice = weeklyReportService;
    }
    @GetMapping("/child/{childId}")
    public WeeklyReport getWeeklyReport(@PathVariable Long childId) {
        return weeklyreportservice.generateReport(childId);
    }
}