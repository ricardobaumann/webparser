package com.github.ricardobaumann.webparser.model;

public class PageResults {
    private final int pInDiv;
    private final int h1Amount;
    private final String url;

    public PageResults(int pInDiv, int h1Amount, String url) {
        this.pInDiv = pInDiv;
        this.h1Amount = h1Amount;
        this.url = url;
    }

    @Override
    public String toString() {
        return "PageResults{" +
                "pInDiv=" + pInDiv +
                ", h1Amount=" + h1Amount +
                ", url='" + url + '\'' +
                '}';
    }
}
