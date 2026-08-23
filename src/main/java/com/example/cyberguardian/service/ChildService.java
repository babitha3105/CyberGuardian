package com.example.cyberguardian.service;

import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.repository.ChildRepository;
import com.example.cyberguardian.repository.ParentRepository;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;

    public ChildService(ChildRepository childRepository,
                        ParentRepository parentRepository) {
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
    }

    public Child saveChild(Child child) {

        Long parentId = child.getParent().getParentId();

        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        child.setParent(parent);

        return childRepository.save(child);
    }
}