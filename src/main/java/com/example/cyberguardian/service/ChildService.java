package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.Child;
import com.example.cyberguardian.entity.Parent;
import com.example.cyberguardian.repository.ChildRepository;
import com.example.cyberguardian.repository.ParentRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
        child.setConnectionCode(generateConnectionCode());
        child.setExtensionStatus("NOT_CONNECTED");

        return childRepository.save(child);
    }
    private String generateConnectionCode() {

        return java.util.UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();
    }
    public Optional<Child> getChildById(Long childId) {
        return childRepository.findById(childId);
    }
}