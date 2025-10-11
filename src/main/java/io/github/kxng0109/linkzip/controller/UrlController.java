package io.github.kxng0109.linkzip.controller;

import io.github.kxng0109.linkzip.service.UrlShorteningService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class UrlController {
    private UrlShorteningService urlShorteningService;

    public UrlController(UrlShorteningService urlShorteningService){
        this.urlShorteningService = urlShorteningService;
    }

    //DTO for the request
    public record ShortenRequest(
            @NotBlank(message = "URL cannot be empty")
            @URL(message = "A valid URL format is required")
            String longUrl
    ){}

    //DTO for the response
    public record ShortenResponse(String shortUrl){}

    @PostMapping("/api/v1/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@Valid @RequestBody ShortenRequest request) {
        String shortCode = urlShorteningService
                .shortenUrl(request.longUrl())
                .getShortCode();

        String shortCodeUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("{uri}")
                .buildAndExpand(shortCode)
                .toUriString();

        ShortenResponse response = new ShortenResponse(shortCodeUri);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    //If the url exists, redirect the user the long url when they fetch for it
    //If not, return 404 not found.
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        Optional<String> longUrl =  urlShorteningService.getLongUrlByShortCode(shortCode);
        if(longUrl.isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(longUrl.get()))
                    .build();
        }
        return ResponseEntity.notFound().build();
    }
}
