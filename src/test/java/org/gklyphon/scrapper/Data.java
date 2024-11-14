package org.gklyphon.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Utility class for providing sample HTML elements and collections of elements
 * for testing purposes in the WebScrapperTest class.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 14-Nov-2024
 */
public class Data {

    /** A sample H1 element with the text "Header1". */
    public static final Element ELEMENT_H1 = new Element("H1").text("Header1");

    /** A sample H2 element with the text "Header2". */
    public static final Element ELEMENT_H2 = new Element("H2").text("Header2");

    /**
     * A collection of sample elements containing H1 and H2 elements
     * with predefined text. Used for simulating parsed elements in tests.
     */
    public static final Elements ELEMENTS = createElements() ;

    /**
     * Creates a collection of Elements by parsing a sample HTML string
     * containing H1 and H2 tags. This method ensures ELEMENTS is
     * populated with non-null elements for test usage.
     *
     * @return a collection of Elements containing H1 and H2 elements
     */
    private static Elements createElements() {
        String html = "<h1>Header 1</h1><h2>Header 2</h2>";
        Document document = Jsoup.parse(html);
        return document.select("h1, h2");
    }
}
