                                                                                                                      package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findByConnectionCode(String connectionCode);
}