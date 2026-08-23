package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.repository.ParentRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class ParentService {

    private final ParentRepository parentRepository;
    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }
    public Parent saveParent(Parent parent) {
        return parentRepository.save(parent);
    }
    public Optional<Parent> getParentById(Long parentId) {
        return parentRepository.findById(parentId);
    }
    public Optional<Parent> getParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }
}