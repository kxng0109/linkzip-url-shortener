package io.github.kxng0109.linkzip.service;

import io.github.kxng0109.linkzip.model.UrlMapping;

import java.util.Optional;

public interface UrlShorteningService {
    UrlMapping shortenUrl(String url);

    Optional<String> getLongUrlByShortCode(String shortCode);
}
