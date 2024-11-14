package org.gklyphon.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Data {

    public static final Element ELEMENT_H1 = new Element("H1").text("Header1");
    public static final Element ELEMENT_H2 = new Element("H2").text("Header2");
    public static final Elements ELEMENTS = createElements() ;

    private static Elements createElements() {
        String html = "<h1>Header 1</h1><h2>Header 2</h2>";
        Document document = Jsoup.parse(html);
        return document.select("h1, h2");
    }
}
