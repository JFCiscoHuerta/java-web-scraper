package org.gklyphon.scrapper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebScrapperTest {

    @Mock
    WebScrapper scrapper;

    @BeforeEach
    void setUp() {
        scrapper.setPageUrl("www.google.com");
    }

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
    void scrapeTitles_shouldThrowsIOException_whenScrapeTitlesFails() {
        doThrow(RuntimeException.class).when(scrapper).scrapeTitles();
        //when(scrapper.scrapeTitles()).thenReturn(Data.ELEMENTS);
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


}