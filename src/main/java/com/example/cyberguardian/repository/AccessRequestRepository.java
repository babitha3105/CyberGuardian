
package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.AccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {
    List<AccessRequest> findByChildChildId(Long childId);
}