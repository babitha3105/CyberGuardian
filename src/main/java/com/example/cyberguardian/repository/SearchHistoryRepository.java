package com.example.cyberguardian.repository;

import com.example.cyberguardian.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchHistoryRepository
        extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findByChildChildId(Long childId);

    List<SearchHistory> findByChildChildIdAndSearchedAtAfterOrderBySearchedAtDesc(
            Long childId,
            LocalDateTime dateTime
    );

    @Query("""
        SELECT COUNT(s)
        FROM SearchHistory s
        WHERE s.child.childId = :childId
          AND s.riskLevel = 'HIGH'
          AND s.searchedAt >= :dateTime
    """)
    long countHighRiskSearches(
            @Param("childId") Long childId,
            @Param("dateTime") LocalDateTime dateTime
    );
}