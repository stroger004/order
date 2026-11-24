package ch.fhnw.bookstore.order;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * Hält pro User-Session einen eigenen Warenkorb.
 * Der Warenkorb wird als Attribut in der HTTP-Session gespeichert,
 * jeder Browser bzw. User hat damit seinen eigenen Warenkorb.
 */
@Service
public class ShoppingCartService {

    static final String CART_ATTRIBUTE = "shoppingCart";

    /**
     * Liefert den Warenkorb der Session. Gibt es noch keinen, wird ein neuer, leerer angelegt.
     */
    public ShoppingCart getCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    /**
     * Legt ein Buch in den Warenkorb der Session.
     */
    public void addBook(HttpSession session, Book book) {
        getCart(session).addBook(book);
    }
}
