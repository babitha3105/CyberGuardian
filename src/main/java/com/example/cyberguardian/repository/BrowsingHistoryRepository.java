package com.example.cyberguardian.repository;
import com.example.cyberguardian.entity.BrowsingHistory;
import com.example.cyberguardian.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Long>  {
    List<BrowsingHistory> findByChildChildId(Long childId);
    List<BrowsingHistory> findByChildChildIdAndVisitedAtAfter(
            Long childId,
            LocalDateTime dateTime
    );
}
