package org.gklyphon.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * WebScrapper is a class that provides functionality to scrape elements from a specified webpage.
 * It includes methods to connect to the page, scrape titles, links, or specific elements using CSS selectors,
 * and save the scraped elements to a file. It supports retrying connections if the first attempt fails.
 * <p>
 * This class can be used to collect various information from webpages such as headers, links, or any other HTML elements.
 * The connection is configurable via the user agent and the URL of the target page.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 12-Nov-2024
 *
 */
public class WebScrapper {

    private String userAgent = null;
    private String pageUrl = null;

    /**
     * Constructor to initialize the WebScrapper with the target page URL.
     * It uses a default user agent string if no user agent is provided.
     *
     * @param pageUrl the URL of the page to scrape.
     */
    public WebScrapper(String pageUrl) {
        this.pageUrl = pageUrl;
        this.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88" +
                "Safari/537.36";
    }

    /**
     * Constructor to initialize the WebScrapper with both the target page URL and a custom user agent.
     *
     * @param pageUrl the URL of the page to scrape.
     * @param userAgent the user agent string to be used for the connection.
     */
    public WebScrapper(String pageUrl, String userAgent) {
        this.pageUrl = pageUrl;
        this.userAgent = userAgent != null ? userAgent : "Mozilla/5.0 (Windows NT 10.0; Win64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";
    }

    /**
     * Establishes a connection to the specified page using the given user agent.
     *
     * @return the document representing the HTML content of the page.
     * @throws IOException if the connection cannot be established.
     */
    private Document connect() throws IOException {
        return Jsoup.connect(pageUrl)
                .userAgent(userAgent)
                .get();
    }

    /**
     * Tries to connect to the page with retry attempts in case of failure.
     *
     * @param retries the number of retry attempts.
     * @return the document representing the HTML content of the page.
     * @throws IOException if the connection cannot be established after all retries.
     */
    private Document connectWithRetry(int retries) throws IOException {
        int attempt = 0;
        int waitTime = 2000;
        while (attempt < retries) {
            try {
                return connect();
            } catch (IOException e) {
                System.err.printf("Connection attempt failed, retrying connection [%s].%n",attempt + 1);
                attempt++;
                try {
                    Thread.sleep(2000);
                    waitTime *= 2;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new IOException("Could not connect after several attempts.");
    }

    /**
     * Tests if a connection can be established with the target page.
     *
     * @return true if the connection is successful, false otherwise.
     */
    public boolean testConnection() {
        try {
            connectWithRetry(1);
            System.out.println("Successfully connected.");
            return true;
        } catch (IOException e) {
            System.err.println("Connection could not be established.");
            return false;
        }
    }

    /**
     * Scrapes titles (h1, h2, h3, h4) from the target page.
     *
     * @return the elements representing the titles on the page.
     */
    public Elements scrapeTitles() {
        try {
            Document document = connectWithRetry(3);
            return document.select("h1, h2, h3, h4");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Scrapes all links (anchor elements with href) from the target page.
     *
     * @return the elements representing the links on the page.
     */
    public Elements scrapeLinks() {
        try {
            Document document = connectWithRetry(3);
            return document.select("a[href]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Scrapes specific elements based on a provided CSS selector.
     *
     * @param selector the CSS selector to match the elements.
     * @return the elements matching the provided selector.
     */
    public Elements scrapeSpecificElements(String selector) {
        try {
            Document document = connectWithRetry(3);
            Elements elements = document.select(selector);
            if (elements.isEmpty()) {
                System.out.printf("No elements found for the selector: %s%n", selector);
            }
            return elements;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the scraped elements to a specified file.
     *
     * @param path the path of the file where the elements will be saved.
     * @param elements the elements to save to the file.
     */
    public void saveElementsToFile(String path, Elements elements) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (Element element : elements) {
                writer.printf("%s, %s%n",element, element.text());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
