package com.github.ricardobaumann.webparser;

import com.github.ricardobaumann.webparser.model.PageResults;
import com.github.ricardobaumann.webparser.repo.DocumentRepo;
import com.github.ricardobaumann.webparser.repo.SiteMapRepo;
import com.github.ricardobaumann.webparser.service.SiteMapValidationService;
import com.github.ricardobaumann.webparser.service.UrlValidationService;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class SiteMapUrlValidationServiceTest {

    @Test
    public void validateAbendblatt() throws ParserConfigurationException, SAXException, IOException {
        List<PageResults> results = new SiteMapValidationService(new SiteMapRepo(), new UrlValidationService(new DocumentRepo())).validate("https://www.abendblatt.de/sitemaps/news.xml", 50);
        System.out.println(results);
    }

    @Test
    public void validateMorgenPost() throws ParserConfigurationException, SAXException, IOException {
        List<PageResults> results = new SiteMapValidationService(new SiteMapRepo(), new UrlValidationService(new DocumentRepo())).validate("https://www.morgenpost.de/sitemaps/news.xml", 50);
        System.out.println(results);
    }
}