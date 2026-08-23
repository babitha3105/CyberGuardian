package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.BrowsingHistory;
import com.example.cyberguardian.repository.BrowsingHistoryRepository;
import org.springframework.stereotype.Service;
@Service
public class BrowsingHistoryService {
    private final BrowsingHistoryRepository browsinghistoryrepository;


    public BrowsingHistoryService(BrowsingHistoryRepository browsinghistoryrepository) {
        this.browsinghistoryrepository = browsinghistoryrepository;
    }

    public BrowsingHistory saveBrowsingHistory(BrowsingHistory browsingHistory)
    {
        return browsinghistoryrepository.save(browsingHistory);
    }
}
