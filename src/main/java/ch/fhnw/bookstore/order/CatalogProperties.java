package ch.fhnw.bookstore.order;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Konfiguration für den Zugriff auf den Catalog-Service.
 * Wird aus application.properties (catalog.url) oder aus der
 * Umgebungsvariable CATALOG_URL gelesen.
 */
@ConfigurationProperties(prefix = "catalog")
public record CatalogProperties(String url) {
}