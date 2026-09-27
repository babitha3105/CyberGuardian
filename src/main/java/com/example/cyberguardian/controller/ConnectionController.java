package com.example.cyberguardian.controller;

import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.service.ConnectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/child-connection")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping
    public Child connectChild(
            @RequestParam String connectionCode) {

        return connectionService
                .findChildByConnectionCode(connectionCode);
    }
}