package ch.fhnw.bookstore.order;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * End-to-End-Test mit Playwright für Suche und Warenkorb.
 * <p>
 * Voraussetzung: Catalog läuft auf Port 8080 und Order auf Port 8081.
 * Der Name endet auf "IT", damit der Test bei "mvn test" nicht automatisch läuft
 * (dort laufen die Apps nicht). Starten in IntelliJ über den grünen Pfeil.
 */
class CatalogPageE2EIT {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    @BeforeAll
    static void startBrowser() {
        playwright = Playwright.create();
        // setHeadless(false) und setSlowMo(500): Browser ist sichtbar und klickt langsam
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(500));
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void openPage() {
        // Neuer Context = neue Session = leerer Warenkorb
        page = browser.newContext().newPage();
    }

    @AfterEach
    void closePage() {
        page.context().close();
    }

    @Test
    void searchBooksAndAddOneToCart() {
        // 1. Browser unter http://localhost:8081 öffnen
        page.navigate("http://localhost:8081");

        // 2. Wert im Suchfeld eingeben
        page.locator("#keywords").fill("tolkien");

        // 3. Such-Button betätigen
        page.locator("#search").click();

        // 4. Prüfen, ob die erwarteten Bücher angezeigt werden
        Locator rows = page.locator("#results tbody tr");
        assertThat(rows).hasCount(2);
        assertThat(page.locator("#results")).containsText("Der Hobbit");
        assertThat(page.locator("#results")).containsText("Der Herr der Ringe");

        // 5. Ein Buch in den Warenkorb legen
        rows.filter(new Locator.FilterOptions().setHasText("Der Hobbit"))
                .locator("button.add-to-cart")
                .click();

        // 6. Prüfen, ob der Warenkorb das ausgewählte Buch enthält
        assertThat(page.locator("#cart-size")).hasText("1");
        assertThat(page.locator("#cart")).containsText("Der Hobbit");
        assertThat(page.locator("#cart")).not().containsText("Der Herr der Ringe");
    }
}
