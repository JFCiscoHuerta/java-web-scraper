package org.gklyphon.scrapper;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the WebScrapper class, using Mockito for mock-based testing.
 * Verifies that each method in WebScrapper behaves as expected, with specific attention to connections and
 * data scraping functionalities for titles, links, and specific elements.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 14-Nov-2024
 *
 */
@ExtendWith(MockitoExtension.class)
class WebScrapperTest {

    @Mock
    WebScrapper scrapper;

    /**
     * Tests if testConnection() returns true when a connection test is successful.
     */
    @Test
    void testConnection_shouldReturnTrue_whenTestConnectionCalled() {
        when(scrapper.testConnection()).thenReturn(true);
        assertTrue(scrapper.testConnection());
        verify(scrapper, atLeastOnce()).testConnection();
    }

    /**
     * Tests if testConnection() returns false when a connection test fails.
     */
    @Test
    void testConnection_shouldReturnFalse_whenTestConnectionFails() {
        when(scrapper.testConnection()).thenReturn(false);
        assertFalse(scrapper.testConnection());
        verify(scrapper).testConnection();
    }

    /**
     * Asserts that testConnection() does not throw an IOException when called.
     */
    @Test
    void testConnection_shouldNotThrowIOException_whenTestConnectionCalled() {
        assertDoesNotThrow(()-> scrapper.testConnection());
        verify(scrapper).testConnection();
    }

    /**
     * Tests if scrapeTitles() returns non-empty elements when called.
     */
    @Test
    void scrapeTitles_shouldReturnElements_whenScrapeTitlesCalled() {
        when(scrapper.scrapeTitles()).thenReturn(Data.ELEMENTS);
        Elements elements = scrapper.scrapeTitles();
        assertFalse(elements.isEmpty());
        verify(scrapper).scrapeTitles();
    }

    /**
     * Verifies that scrapeTitles() throws a RuntimeException when the method fails.
     */
    @Test
    void scrapeTitles_shouldThrowsRuntimeException_whenScrapeTitlesFails() {
        doThrow(RuntimeException.class).when(scrapper).scrapeTitles();
        assertThrows(RuntimeException.class, ()->scrapper.scrapeTitles());
        verify(scrapper).scrapeTitles();
    }

    /**
     * Tests if scrapeLinks() returns non-empty elements when scrapeLinks() is called.
     */
    @Test
    void scrapeLinks_shouldReturnElements_whenScrapeLinksCalled() {
        when(scrapper.scrapeLinks()).thenReturn(Data.ELEMENTS);
        Elements elements = scrapper.scrapeLinks();
        assertFalse(elements.isEmpty());
        verify(scrapper).scrapeLinks();
    }

    /**
     * Ensures that scrapeLinks() throws an IOException when an error occurs.
     */
    @Test
    void scrapeLinks_shouldThrowsIOException_whenScrapeLinksFails() {
        doThrow(RuntimeException.class).when(scrapper).scrapeLinks();
        assertThrows(RuntimeException.class, ()->scrapper.scrapeLinks());
        verify(scrapper).scrapeLinks();
    }

    /**
     * Tests if scrapeSpecificElements() returns elements matching the specified tags.
     */
    @Test
    void scrapeSpecificElements_shouldReturnElements_whenScrapeSpecificElementsCalled() {
        when(scrapper.scrapeSpecificElements(anyString())).thenReturn(Data.ELEMENTS);
        Elements elements = scrapper.scrapeSpecificElements("h1, h2");
        assertFalse(elements.isEmpty());
        assertEquals("Header 1 Header 2", elements.text());
        verify(scrapper).scrapeSpecificElements(anyString());
    }

    /**
     * Ensures that scrapeSpecificElements() throws a RuntimeException when an error occurs.
     */
    @Test
    void scrapeSpecificElements_shouldThrowsRuntimeException_whenScrapeSpecificElementsCalled() {
        doThrow(RuntimeException.class).when(scrapper).scrapeSpecificElements(anyString());
        assertThrows(RuntimeException.class, ()-> scrapper.scrapeSpecificElements("h1 h2"));
        verify(scrapper).scrapeSpecificElements(anyString());
    }

}