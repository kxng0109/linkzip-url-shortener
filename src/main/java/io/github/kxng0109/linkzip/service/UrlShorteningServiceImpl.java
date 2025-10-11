package io.github.kxng0109.linkzip.service;

import io.github.kxng0109.linkzip.model.UrlMapping;
import io.github.kxng0109.linkzip.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 7;
    private static final SecureRandom random = new SecureRandom();

    private final UrlMappingRepository urlMappingRepository;

    public UrlShorteningServiceImpl(UrlMappingRepository urlMappingRepository){
        this.urlMappingRepository = urlMappingRepository;
    }

    @Override
    public UrlMapping shortenUrl(String longUrl) {
        String shortCode = generateUniqueShortCode();

        UrlMapping urlMapping = UrlMapping.builder()
                .longUrl(longUrl)
                .shortCode(shortCode)
                .build();

        return urlMappingRepository.save(urlMapping);
    }

    @Override
    public Optional<String> getLongUrlByShortCode(String shortCode) {
       return urlMappingRepository.findByShortCode(shortCode)
               .map(UrlMapping::getLongUrl);
    }

    /*Generates and return a pseudo random short url as long as it's not in the database*/
    private String generateUniqueShortCode() {
        String shortCode = "";
        do {
            StringBuilder builder = new StringBuilder(SHORT_CODE_LENGTH);
            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
                int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
                builder.append(ALLOWED_CHARACTERS.charAt(randomIndex));
            }
            shortCode = builder.toString();
        }
        while (urlMappingRepository.existsByShortCode(shortCode));
        return shortCode;
    }
}
