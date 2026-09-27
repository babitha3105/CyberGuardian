package com.example.cyberguardian.repository;
import com.example.cyberguardian.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByChildChildId(Long childId);
    List<SearchHistory> findByChildChildIdAndSearchedAtAfterOrderBySearchedAtDesc(
            Long childId,
            LocalDateTime dateTime
    );
}