package com.example.cyberguardian.repository;
import com.example.cyberguardian.entity.BrowsingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Long>  {
}
