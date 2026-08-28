package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByParentParentId(Long parentId);
}