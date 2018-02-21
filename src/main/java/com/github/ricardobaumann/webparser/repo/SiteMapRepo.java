package com.github.ricardobaumann.webparser.repo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SiteMapRepo {

    private final SAXParser saxParser;

    public SiteMapRepo() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
    }

    public List<String> extractUrls(String siteMapUrl, int maxResults) throws IOException, SAXException {
        Handler handler = new Handler(maxResults);
        try {
            saxParser.parse(siteMapUrl, handler);
        } catch (ParsingTerminationException e) {
            System.out.println("Max results reached");
        }
        return handler.getLocList();
    }

    private static final class ParsingTerminationException extends RuntimeException {

    }

    private static final class Handler extends DefaultHandler {

        private boolean insideLoc = false;
        private final List<String> locList = new LinkedList<>();
        private int maxResults;

        Handler(int maxResults) {
            this.maxResults = maxResults;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            insideLoc = qName.equalsIgnoreCase("loc");
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (insideLoc) {
                String loc = new String(ch, start, length).trim().replaceAll("\\n", "");
                if (!loc.isEmpty()) {
                    locList.add(loc);
                    if (maxResults-- <= 1) {
                        throw new ParsingTerminationException();
                    }
                }
            }
        }

        List<String> getLocList() {
            return locList;
        }
    }

}
