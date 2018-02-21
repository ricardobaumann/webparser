package com.github.ricardobaumann.webparser.repo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DocumentRepo {

    public Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}
