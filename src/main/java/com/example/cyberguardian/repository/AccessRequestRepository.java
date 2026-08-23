
package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.AccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {

}