package com.example.cyberguardian.repository;
import com.example.cyberguardian.entity.WebsiteList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface WebsiteListRepository extends JpaRepository<WebsiteList,Long> {
    Optional<WebsiteList> findByDomainAndListType(String domain, String listType);

}
