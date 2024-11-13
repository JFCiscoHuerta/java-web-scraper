package org.gklyphon;

import org.gklyphon.scrapper.WebScrapper;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebScrapper webScrapper = new WebScrapper("your-web-page-link");

        if (webScrapper.testConnection()) {
            Elements elements = webScrapper.scrapeLinks();
            webScrapper.saveElementsToFile("target-element.txt", elements);
        }

    }
}