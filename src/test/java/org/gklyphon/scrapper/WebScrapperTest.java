package org.gklyphon.scrapper;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebScrapperTest {

    @Mock
    WebScrapper scrapper;

    @Test
    void testConnection_shouldReturnTrue_whenTestConnectionCalled() {
        when(scrapper.testConnection()).thenReturn(true);
        assertTrue(scrapper.testConnection());
        verify(scrapper, atLeastOnce()).testConnection();
    }

    @Test
    void testConnection_shouldReturnFalse_whenTestConnectionFails() {
        when(scrapper.testConnection()).thenReturn(false);
        assertFalse(scrapper.testConnection());
        verify(scrapper).testConnection();
    }

    @Test
    void testConnection_shouldNotThrowIOException_whenTestConnectionCalled() {
        assertDoesNotThrow(()-> scrapper.testConnection());
        verify(scrapper).testConnection();
    }

    @Test
    void scrapeTitles_shouldReturnElements_whenScrapeTitlesCalled() {
        when(scrapper.scrapeTitles()).thenReturn(Data.ELEMENTS);
        Elements elements = scrapper.scrapeTitles();
        assertFalse(elements.isEmpty());
        verify(scrapper).scrapeTitles();
    }

    @Test
    void scrapeTitles_shouldThrowsRuntimeException_whenScrapeTitlesFails() {
        doThrow(RuntimeException.class).when(scrapper).scrapeTitles();
        assertThrows(RuntimeException.class, ()->scrapper.scrapeTitles());
        verify(scrapper).scrapeTitles();
    }

    @Test
    void scrapeLinks_shouldReturnElements_whenScrapeLinksCalled() {
        when(scrapper.scrapeLinks()).thenReturn(Data.ELEMENTS);
        Elements elements = scrapper.scrapeLinks();
        assertFalse(elements.isEmpty());
        verify(scrapper).scrapeLinks();
    }

    @Test
    void scrapeLinks_shouldThrowsIOException_whenScrapeLinksFails() {
        doThrow(RuntimeException.class).when(scrapper).scrapeLinks();
        assertThrows(RuntimeException.class, ()->scrapper.scrapeLinks());
        verify(scrapper).scrapeLinks();
    }

    @Test
    void scrapeSpecificElements_shouldReturnElements_whenScrapeSpecificElementsCalled() {
        when(scrapper.scrapeSpecificElements(anyString())).thenReturn(Data.ELEMENTS);
        Elements elements = scrapper.scrapeSpecificElements("h1, h2");
        assertFalse(elements.isEmpty());
        assertEquals("Header 1 Header 2", elements.text());
        verify(scrapper).scrapeSpecificElements(anyString());
    }

    @Test
    void scrapeSpecificElements_shouldThrowsRuntimeException_whenScrapeSpecificElementsCalled() {
        doThrow(RuntimeException.class).when(scrapper).scrapeSpecificElements(anyString());
        assertThrows(RuntimeException.class, ()-> scrapper.scrapeSpecificElements("h1 h2"));
        verify(scrapper).scrapeSpecificElements(anyString());
    }

}