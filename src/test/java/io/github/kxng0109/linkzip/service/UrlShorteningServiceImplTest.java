package io.github.kxng0109.linkzip.service;

import io.github.kxng0109.linkzip.model.UrlMapping;
import io.github.kxng0109.linkzip.repository.UrlMappingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShorteningServiceImplTest {
    private final String longUrl = "https://www.thisurlislong.com/long-the-longest-url-sha";
    private final String shortCode = "E97RTe";

    UrlMapping testUrlMapping = new UrlMapping(
            1L, longUrl, shortCode , LocalDateTime.now()
    );

    @Mock
    UrlMappingRepository urlMappingRepository;

    @InjectMocks
    UrlShorteningServiceImpl urlShorteningServiceImpl;

    @Test
    void shouldReturnAUrlMappingWhenShortenUrlIsCalled() {
        when(urlMappingRepository.existsByShortCode(any(String.class)))
                .thenReturn(false);
        when(urlMappingRepository.save(any(UrlMapping.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UrlMapping result = urlShorteningServiceImpl.shortenUrl(longUrl);

        assertNotNull(result);
        assertEquals(longUrl, result.getLongUrl());
        assertNotNull(result.getShortCode());
        assertEquals(8, result.getShortCode().length());

        verify(urlMappingRepository).save(any(UrlMapping.class));
        verify(urlMappingRepository).existsByShortCode(any(String.class));
    }

    @Test
    void shouldReturnLongUrlWhenShortCodeIsProvided() {
        when(urlMappingRepository.findByShortCode(any(String.class)))
                .thenReturn(Optional.of(testUrlMapping));

        Optional<String> result = urlShorteningServiceImpl.getLongUrlByShortCode(shortCode);

        assertTrue(result.isPresent());
        assertEquals(result.get(), longUrl);

        verify(urlMappingRepository).findByShortCode(shortCode);
    }

}
