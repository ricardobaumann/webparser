package com.github.ricardobaumann.webparser.service;

import com.github.ricardobaumann.webparser.exception.PageValidationException;
import com.github.ricardobaumann.webparser.model.PageResults;
import com.github.ricardobaumann.webparser.repo.DocumentRepo;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UrlValidationService {

    private final DocumentRepo documentRepo;

    public UrlValidationService(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }

    public static void main(String[] args) throws IOException, PageValidationException {
        new UrlValidationService(new DocumentRepo()).validate("https://www.abendblatt.de/vermischtes/article213509657/Film-ueber-Breivik-Massaker-von-Utoya-sorgt-fuer-Diskussionen.html");
    }

    public PageResults validate(String url) throws IOException, PageValidationException {
        try {
            Document doc = documentRepo.getDocument(url);
            Elements articleBodies = doc.body().select("div[class$=article__body]");
            if (articleBodies.size() == 0) {
                throw new PageValidationException("No article body");
            }
            int pInsideDiv = articleBodies.select("p").size();
            int h1Amount = doc.select("h1").size();
            if (h1Amount < 1) {
                throw new PageValidationException("H1 amount should be >= 1");
            }
            Elements ps = doc.select("p");
            if (ps.select("img").size() > 0) {
                throw new PageValidationException("<img> is not allowed inside <p>");
            }
            if (ps.select("div").size() > 0) {
                throw new PageValidationException("<div> is not allowed inside <p>");
            }
            if (ps.select("table").size() > 0) {
                throw new PageValidationException("<table> is not allowed inside <p>");
            }
            return new PageResults(pInsideDiv, h1Amount, url);
        } catch (HttpStatusException e) {
            throw new PageValidationException(String.format("Url %s not found", url));
        }
    }


}
