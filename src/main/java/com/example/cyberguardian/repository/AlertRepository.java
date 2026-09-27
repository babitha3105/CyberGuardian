package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByParentParentId(Long parentId);
    boolean existsByChildChildIdAndAlertType(
            Long childId,
            String alertType
    );
    List<Alert> findByChildChildIdAndCreatedAtAfter(
            Long childId,
            LocalDateTime dateTime
    );
    boolean existsByChildChildIdAndAlertTypeAndIsReadFalse(
            Long childId,
            String alertType
    );
}