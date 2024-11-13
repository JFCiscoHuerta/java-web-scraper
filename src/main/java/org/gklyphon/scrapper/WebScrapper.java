package org.gklyphon.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WebScrapper {

    private String userAgent = null;
    private String pageUrl = null;

    public WebScrapper(String pageUrl) {
        this.pageUrl = pageUrl;
        this.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88" +
                "Safari/537.36";
    }

    public WebScrapper(String pageUrl, String userAgent) {
        this.pageUrl = pageUrl;
        this.userAgent = userAgent != null ? userAgent : "Mozilla/5.0 (Windows NT 10.0; Win64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88" +
                "Safari/537.36";
    }

    private Document connect() throws IOException {
        return Jsoup.connect(pageUrl)
                .userAgent(userAgent)
                .get();
    }

    private Document connectWithRetry(int retries) throws IOException {
        int attempt = 0;
        int waitTime = 2000;
        while (attempt < retries) {
            try {
                return connect();
            } catch (IOException e) {
                System.err.printf("Intento de conexion fallido, reintentando conexion [%s].%n",attempt + 1);
                attempt++;
                try {
                    Thread.sleep(2000);
                    waitTime *= 2;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new IOException("No se pudo conectar despues de varios intentos.");
    }

    public boolean testConnection() {
        try {
            connectWithRetry(1);
            System.out.println("Successfully connection.");
            return true;
        } catch (IOException e) {
            System.err.println("Connection could not be established.");
            return false;
        }
    }

    public Elements scrapeTitles() {
        try {
            Document document = connectWithRetry(3);
            return document.select("h1, h2, h3, h4");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Elements scrapeLinks() {
        try {
            Document document = connectWithRetry(3);
            return document.select("a[href]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Elements scrapeSpecificElements(String selector) {
        try {
            Document document = connectWithRetry(3);
            Elements elements = document.select(selector);
            if (elements.isEmpty()) {
                System.out.printf("No se encontraron elementos para el selector: %s%n", selector);
            }
            return elements;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
