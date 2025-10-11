package io.github.kxng0109.linkzip.repository;

import io.github.kxng0109.linkzip.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    /*Checks if a UrlMapping with the given shortenedUrl exists.
    It will return true if it does and false if it doesn't*/

    boolean existsByShortCode(String shortenUrl);

    Optional<UrlMapping> findByShortCode(String shortUrl);
}
