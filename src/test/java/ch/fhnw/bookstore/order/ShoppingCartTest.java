package ch.fhnw.bookstore.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShoppingCartTest {

    private final Book hobbit = new Book("1", "Der Hobbit", "Bilbo und die Zwerge.", "J.R.R. Tolkien");
    private final Book physiker = new Book("2", "Die Physiker", "Drei Physiker im Irrenhaus.", "Friedrich Dürrenmatt");

    @Test
    void newCartIsEmpty() {
        ShoppingCart cart = new ShoppingCart();

        assertThat(cart.isEmpty()).isTrue();
        assertThat(cart.getSize()).isZero();
    }

    @Test
    void addBookPutsBookIntoCart() {
        ShoppingCart cart = new ShoppingCart();

        cart.addBook(hobbit);
        cart.addBook(physiker);

        assertThat(cart.getSize()).isEqualTo(2);
        assertThat(cart.getBooks()).containsExactly(hobbit, physiker);
    }

    @Test
    void booksCannotBeChangedFromOutside() {
        ShoppingCart cart = new ShoppingCart();
        cart.addBook(hobbit);

        assertThatThrownBy(() -> cart.getBooks().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
