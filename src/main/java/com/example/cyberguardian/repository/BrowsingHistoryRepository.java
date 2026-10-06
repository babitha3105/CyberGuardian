package com.example.cyberguardian.repository;
import com.example.cyberguardian.entity.BrowsingHistory;
import com.example.cyberguardian.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Long>  {
    List<BrowsingHistory> findByChildChildId(Long childId);
    List<BrowsingHistory> findByChildChildIdAndVisitedAtAfter(
            Long childId,
            LocalDateTime dateTime
    );
    @Query("""
    SELECT COUNT(b)
    FROM BrowsingHistory b
    WHERE b.child.childId = :childId
      AND b.actionTaken = 'BLOCK'
      AND b.visitedAt >= :dateTime
""")
    long countBlockedAttempts(
            @Param("childId") Long childId,
            @Param("dateTime") LocalDateTime dateTime
    );
}
