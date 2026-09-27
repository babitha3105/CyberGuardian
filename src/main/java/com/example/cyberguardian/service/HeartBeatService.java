package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.repository.ChildRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HeartBeatService {

    private final ChildRepository childRepository;

    public HeartBeatService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public Child updateHeartbeat(Long childId) {

        Child child = childRepository.findById(childId)
                .orElseThrow(() ->
                        new RuntimeException("Child not found"));

        child.setExtensionStatus("CONNECTED");
        child.setLastSeen(LocalDateTime.now());

        return childRepository.save(child);
    }
}