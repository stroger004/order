package ch.fhnw.bookstore.order;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Zeigt die Catalog-Seite mit Suchfeld, Such-Button, Resultattabelle und Warenkorb.
 */
@Controller
public class CatalogController {

    private final CatalogClient catalogClient;
    private final ShoppingCartService shoppingCartService;

    public CatalogController(CatalogClient catalogClient, ShoppingCartService shoppingCartService) {
        this.catalogClient = catalogClient;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping({"/", "/catalog"})
    public String catalog(@RequestParam(name = "keywords", required = false) String keywords,
                          HttpSession session, Model model) {
        List<Book> books = List.of();

        // Erst suchen, wenn der Such-Button geklickt wurde
        if (keywords != null) {
            try {
                books = catalogClient.searchBooks(keywords);
            } catch (RestClientException e) {
                model.addAttribute("error", "Der Catalog-Service ist nicht erreichbar.");
            }
        }

        model.addAttribute("keywords", keywords);
        model.addAttribute("books", books);
        model.addAttribute("cart", shoppingCartService.getCart(session));
        return "catalog";
    }

    /**
     * Button «In den Warenkorb»: legt das Buch in den Warenkorb der Session
     * und zeigt danach wieder dieselbe Suche an.
     */
    @PostMapping("/cart")
    public String addToCart(@RequestParam String isbn,
                            @RequestParam String title,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) String keywords,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        shoppingCartService.addBook(session, new Book(isbn, title, description, author));

        if (keywords != null) {
            redirectAttributes.addAttribute("keywords", keywords);
        }
        return "redirect:/catalog";
    }
}
