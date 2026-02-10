package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)

public class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("http://localhost:%d", serverPort);
    }

    @Test
    void testCreateProductSuccessfully(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/product/create");

        // Wait for page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nameInput")));

        // Find form elements
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Fill in product details
        nameInput.clear();
        nameInput.sendKeys("Sampo Cap Bambang");

        quantityInput.clear();
        quantityInput.sendKeys("100");

        // Submit the form
        submitButton.click();

        // Wait for redirect to product list page
        wait.until(ExpectedConditions.urlContains("/product/list"));

        // Verify we're on the product list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"),
                "Should redirect to product list page after creating product");

        // Verify the product appears in the list
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.table")));

        List<WebElement> productRows = driver.findElements(By.cssSelector("table tbody tr"));
        assertTrue(productRows.size() > 0, "Product list should contain at least one product");

        // Find the row containing our product
        boolean productFound = false;
        for (WebElement row : productRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                String productName = cells.get(0).getText();
                String productQuantity = cells.get(1).getText();

                if (productName.equals("Sampo Cap Bambang") && productQuantity.equals("100")) {
                    productFound = true;
                    break;
                }
            }
        }

        assertTrue(productFound, "Created product should appear in the product list");
    }

}
