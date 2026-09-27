
package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.AccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {
    List<AccessRequest> findByChildChildId(Long childId);
    Optional<AccessRequest> findByChildChildIdAndUrlAndStatus(
            Long childId,
            String url,
            String status
    );
}