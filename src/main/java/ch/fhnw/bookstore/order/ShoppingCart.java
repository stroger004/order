package ch.fhnw.bookstore.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Warenkorb eines Users. Enthält die Bücher, die der User in den Warenkorb gelegt hat.
 */
public class ShoppingCart {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Liefert die Bücher im Warenkorb (nur lesend).
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public int getSize() {
        return books.size();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }
}
