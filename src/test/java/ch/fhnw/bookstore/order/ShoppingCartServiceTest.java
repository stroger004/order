package ch.fhnw.bookstore.order;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartServiceTest {

    private final ShoppingCartService service = new ShoppingCartService();
    private final Book hobbit = new Book("1", "Der Hobbit", "Bilbo und die Zwerge.", "J.R.R. Tolkien");

    @Test
    void newSessionGetsEmptyCart() {
        MockHttpSession session = new MockHttpSession();

        assertThat(service.getCart(session).isEmpty()).isTrue();
    }

    @Test
    void sameSessionKeepsSameCart() {
        MockHttpSession session = new MockHttpSession();

        service.addBook(session, hobbit);

        assertThat(service.getCart(session).getBooks()).containsExactly(hobbit);
    }

    @Test
    void differentSessionsHaveSeparateCarts() {
        MockHttpSession userA = new MockHttpSession();
        MockHttpSession userB = new MockHttpSession();

        service.addBook(userA, hobbit);

        assertThat(service.getCart(userA).getSize()).isEqualTo(1);
        assertThat(service.getCart(userB).isEmpty()).isTrue();
    }
}
