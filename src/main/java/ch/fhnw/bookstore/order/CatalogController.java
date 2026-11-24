package ch.fhnw.bookstore.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * Zeigt die Catalog-Seite mit Suchfeld, Such-Button und Resultattabelle.
 */
@Controller
public class CatalogController {

    private final CatalogClient catalogClient;

    public CatalogController(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
    }

    @GetMapping({"/", "/catalog"})
    public String catalog(@RequestParam(name = "keywords", required = false) String keywords, Model model) {
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
        return "catalog";
    }
}
