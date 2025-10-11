package io.github.kxng0109.linkzip.service;

import io.github.kxng0109.linkzip.model.UrlMapping;
import io.github.kxng0109.linkzip.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    private final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SHORT_CODE_LENGTH = 7;
    private final SecureRandom random = new SecureRandom();

    private UrlMappingRepository urlMappingRepository;

    public UrlShorteningServiceImpl(UrlMappingRepository umr){
        this.urlMappingRepository = umr;
    }

    @Override
    public UrlMapping shortenUrl(String originalUrl) {
        String shortenedUrl = generateUniqueShortUrl();

        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortenUrl(shortenedUrl)
                .build();

        return urlMappingRepository.save(urlMapping);
    }

    /*Generates and return a pseudo random short url as long as it's not in the database*/
    private String generateUniqueShortUrl() {
        String shortenedUrl = "";
        do {
            StringBuilder builder = new StringBuilder(SHORT_CODE_LENGTH);
            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
                int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
                builder.append(ALLOWED_CHARACTERS.charAt(randomIndex));
            }
            shortenedUrl = builder.toString();
        }
        while (urlMappingRepository.existsByShortenUrl(shortenedUrl));
        return shortenedUrl;
    }
}
