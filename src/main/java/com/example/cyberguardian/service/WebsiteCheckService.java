package com.example.cyberguardian.service;
import com.example.cyberguardian.entity.AccessRequest;
import com.example.cyberguardian.entity.WebsiteList;
import com.example.cyberguardian.repository.WebsiteListRepository;
import com.example.cyberguardian.repository.PolicyRepository;
import com.example.cyberguardian.entity.Policy;
import com.example.cyberguardian.repository.AccessRequestRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.cyberguardian.repository.UrlhausThreatRepository;
import com.example.cyberguardian.repository.OpenPhishThreatRepository;
import java.net.URI;
import com.example.cyberguardian.dto.WebsiteCheckResponse;

@Service
public class WebsiteCheckService {
    private final UrlhausThreatRepository urlhausThreatRepository;
    private final PolicyRepository policyrepository;
    private final WebsiteListRepository websitelistrepository;
    private final SafeBrowsingService safeBrowsingService;
    private final AccessRequestRepository accessrequestrepository;
    private final OpenPhishThreatRepository openPhishThreatRepository;
    public WebsiteCheckService(UrlhausThreatRepository urlhausThreatRepository, PolicyRepository policyrepository, WebsiteListRepository websitelistrepository, SafeBrowsingService safeBrowsingService, AccessRequestRepository accessrequestrepository, OpenPhishThreatRepository openPhishThreatRepository) {
        this.urlhausThreatRepository = urlhausThreatRepository;
        this.policyrepository = policyrepository;
        this.websitelistrepository = websitelistrepository;
        this.safeBrowsingService = safeBrowsingService;
        this.accessrequestrepository = accessrequestrepository;
        this.openPhishThreatRepository = openPhishThreatRepository;
    }
    public WebsiteCheckResponse checkWebsite(Long childId, String url)
    {
        String domain = extractDomain(url);

        // 1. Check Google Safe Browsing
        if (safeBrowsingService.isThreat(url)) {
            return new WebsiteCheckResponse("BLOCK", "SAFE_BROWSING");
        }
// 2. Check URLhaus exact URL
        if (urlhausThreatRepository.findByUrl(url).isPresent()) {
            return new WebsiteCheckResponse("BLOCK", "URLHAUS");
        }
        // 3. Check OpenPhish exact URL
        if (openPhishThreatRepository.findByUrl(url).isPresent()) {
            return new WebsiteCheckResponse("BLOCK", "OPENPHISH");
        }
        // 4. Check approved access request
        Optional<AccessRequest> approvedRequest =
                accessrequestrepository.findByChildChildIdAndUrlAndStatus(
                        childId, url, "APPROVED");

        //System.out.println("CHECK URL: " + url);
       // System.out.println("APPROVED REQUEST FOUND: " + approvedRequest.isPresent());


        if (approvedRequest.isPresent()) {
            return new WebsiteCheckResponse("ALLOW", "APPROVED_REQUEST");
        }

        // 5. Check policy
        Optional<Policy> policy =
                policyrepository.findByChildChildIdAndTargetValue(
                        childId, domain);

        if (policy.isPresent()) {
            String action = policy.get().getAction();

            if ("BLOCK".equals(action)) {
                return new WebsiteCheckResponse("BLOCK", "PARENT_POLICY");
            }

            return new WebsiteCheckResponse(action, "PARENT_POLICY");
        }

        // 6. Check blacklist
        Optional<WebsiteList> blacklist =
                websitelistrepository.findByDomainAndListType(
                        domain, "BLACKLIST");

        if (blacklist.isPresent()) {
            return new WebsiteCheckResponse("BLOCK", "BLACKLIST");
        }

        // 7. Check whitelist
        Optional<WebsiteList> whitelist =
                websitelistrepository.findByDomainAndListType(
                        domain, "WHITELIST");

        if (whitelist.isPresent()) {
            return new WebsiteCheckResponse("ALLOW", "WHITELIST");
        }

        // 8. Default
        return new WebsiteCheckResponse("ALLOW", "DEFAULT");    }
    private String extractDomain(String url) {

        try {
            URI uri = new URI(url);
            String host = uri.getHost();

            if (host == null) {
                throw new RuntimeException("Invalid URL");
            }

            if (host.startsWith("www.")) {
                host = host.substring(4);
            }

            return host;

        } catch (Exception e) {
            throw new RuntimeException("Invalid URL");
        }
    }
}
