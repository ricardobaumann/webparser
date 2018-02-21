package com.github.ricardobaumann.webparser.service;

import com.github.ricardobaumann.webparser.exception.PageValidationException;
import com.github.ricardobaumann.webparser.model.PageResults;
import com.github.ricardobaumann.webparser.repo.SiteMapRepo;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SiteMapValidationService {

    private final SiteMapRepo siteMapRepo;
    private final UrlValidationService urlValidationService;

    public SiteMapValidationService(SiteMapRepo siteMapRepo, UrlValidationService urlValidationService) {
        this.siteMapRepo = siteMapRepo;
        this.urlValidationService = urlValidationService;
    }


    public List<PageResults> validate(String siteMapUrl, int maxResults) throws IOException, SAXException {
        List<String> urls = siteMapRepo.extractUrls(siteMapUrl, maxResults);
        List<PageResults> pageResults = new LinkedList<>();
        for (String url : urls) {
            System.out.println(String.format("Processing url %s", url));
            try {
                PageResults results = urlValidationService.validate(url);
                System.out.println(String.format("Url %s is valid with results: %s", url, results));
                pageResults.add(results);
            } catch (PageValidationException e) {
                System.err.println(String.format("Failed to validate %s due to %s", url, e.getMessage()));
                break;
            } catch (IllegalArgumentException e) {
                System.err.println(String.format("An invalid url was fetched: %s", url));
            }
        }
        return pageResults;
    }

}
