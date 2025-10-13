package io.github.kxng0109.linkzip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kxng0109.linkzip.model.UrlMapping;
import io.github.kxng0109.linkzip.repository.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UrlMappingRepository urlMappingRepository;


    @BeforeEach
    void setup() {
        urlMappingRepository.deleteAll();
    }

    @Test
    void shouldCreateShortUrlAndReturn201() throws Exception {
        String longUrl = "https://google.com";
        UrlController.ShortenRequest request = new UrlController.ShortenRequest(longUrl);

        mockMvc.perform(post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl", containsString("http://localhost/")));

        assertEquals(1, urlMappingRepository.findAll().size());
    }

    @Test
    void shouldReturn400WhenUrlIsInvalid() throws Exception {
        String longInvalidUrl = "not-a-valid-url";
        UrlController.ShortenRequest request = new UrlController.ShortenRequest(longInvalidUrl);

        mockMvc.perform(post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.longUrl", containsString("A valid URL format is required")));
    }

    @Test
    void shouldReturn400WhenUrlIsEmpty() throws Exception {
        String longEmptyUrl = "";
        UrlController.ShortenRequest request = new UrlController.ShortenRequest(longEmptyUrl);

        mockMvc.perform(post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.longUrl", containsString("URL cannot be empty")));
    }

    @Test
    void shouldRedirectToLongUrlWhenShortCodeExists() throws Exception {
        String longUrl = "https://google.com";
        String shortCode = "test123";
        UrlMapping newTestMapping = UrlMapping.builder()
                .longUrl(longUrl)
                .shortCode(shortCode)
                .build();

        urlMappingRepository.save(newTestMapping);

        mockMvc.perform(get("/{shortCode}", shortCode))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(longUrl));

    }

    @Test
    void shouldReturn404WhenShortCodeDoesNotExist() throws Exception {
        mockMvc.perform(get("/notAValidCodeThough"))
                .andExpect(status().isNotFound());
    }
}
