package org.gklyphon;

import org.gklyphon.scrapper.WebScrapper;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebScrapper webScrapper = new WebScrapper("https://jsoup.org/cookbook/introduction/parsing-a-document");

        if (webScrapper.testConnection()) {
            Elements elements = webScrapper.scrapeLinks();
            webScrapper.saveElementsToFile("target-element.txt", elements);
        }

    }
}