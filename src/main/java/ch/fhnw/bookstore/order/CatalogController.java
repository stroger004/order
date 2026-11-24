package ch.fhnw.bookstore.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Zeigt die Catalog-Seite mit Suchfeld, Such-Button und Resultattabelle.
 */
@Controller
public class CatalogController {

    @GetMapping({"/", "/catalog"})
    public String catalog(@RequestParam(name = "keywords", required = false) String keywords, Model model) {
        // Die Suche über die REST-API des Catalog-Service folgt in Task #141.
        List<Book> books = List.of();

        model.addAttribute("keywords", keywords);
        model.addAttribute("books", books);
        return "catalog";
    }
}
