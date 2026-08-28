package com.example.cyberguardian.repository;
import com.example.cyberguardian.entity.BrowsingHistory;
import com.example.cyberguardian.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Long>  {
    List<BrowsingHistory> findByChildChildId(Long childId);

}
