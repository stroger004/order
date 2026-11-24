package ch.fhnw.bookstore.order;

/**
 * Ein Buch, wie es der Catalog-Service als JSON liefert.
 */
public record Book(String isbn, String title, String description, String author) {
}
