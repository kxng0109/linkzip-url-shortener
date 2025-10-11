package io.github.kxng0109.linkzip.service;

import io.github.kxng0109.linkzip.model.UrlMapping;

public interface UrlShorteningService {
    UrlMapping shortenUrl(String url);
}
