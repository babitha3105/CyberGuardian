package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.repository.ChildRepository;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

    private final ChildRepository childRepository;

    public ConnectionService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public Child findChildByConnectionCode(String connectionCode) {

        Child child = childRepository.findByConnectionCode(connectionCode)
                .orElseThrow(() ->
                        new RuntimeException("Invalid connection code"));

        child.setExtensionStatus("CONNECTED");
        child.setLastSeen(java.time.LocalDateTime.now());

        return childRepository.save(child);
    }
}