package com.example.cyberguardian.service;
import com.example.cyberguardian.repository.WebsiteListRepository;
import com.example.cyberguardian.entity.WebsiteList;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class WebsiteListService {
    private final WebsiteListRepository websitelistrepository;

    public WebsiteListService(WebsiteListRepository websitelistrepository) {
        this.websitelistrepository = websitelistrepository;
    }
    public WebsiteList saveWebsiteList(WebsiteList websiteList) {
        return websitelistrepository.save(websiteList);
    }

    public List<WebsiteList> getAllWebsiteLists() {
        return websitelistrepository.findAll();
    }

    public Optional<WebsiteList> findByDomainAndListType(
            String domain,
            String listType) {

        return websitelistrepository.findByDomainAndListType(domain, listType);
    }
}
