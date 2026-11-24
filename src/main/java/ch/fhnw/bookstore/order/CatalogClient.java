package ch.fhnw.bookstore.order;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Ruft die REST-API der Buchsuche im Catalog-Service auf.
 * Die URL kommt aus der Konfiguration (CatalogProperties).
 */
@Service
public class CatalogClient {

    private final RestClient restClient;

    public CatalogClient(CatalogProperties properties) {
        this.restClient = RestClient.builder()
                .baseUrl(properties.url())
                .build();
    }

    /**
     * Beispiel: GET http://localhost:8080/api/books?keywords=tolkien ring
     */
    public List<Book> searchBooks(String keywords) {
        List<Book> books = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/books")
                        .queryParam("keywords", keywords)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {});
        return books != null ? books : List.of();
    }
}