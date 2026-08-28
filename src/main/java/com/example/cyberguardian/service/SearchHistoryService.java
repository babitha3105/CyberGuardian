package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.SearchHistory;
import com.example.cyberguardian.repository.SearchHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryService {
    private final SearchHistoryRepository searchhistoryrepository;

    public SearchHistoryService(SearchHistoryRepository searchhistoryrepository) {
        this.searchhistoryrepository = searchhistoryrepository;
    }

    public SearchHistory saveSearchHistory(SearchHistory searchhistory)
    {
        return searchhistoryrepository.save(searchhistory);
    }
    public List<SearchHistory> getSearchHistoryByChildId(Long childId) {
        return searchhistoryrepository.findByChildChildId(childId);
    }
}
