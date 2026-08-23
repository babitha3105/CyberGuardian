package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}