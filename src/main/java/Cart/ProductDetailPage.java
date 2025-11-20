package Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage {

    private WebDriver driver;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
    }

    // ===== Locators =====

    private By quantityInput = By.id("quantity");
    // locator الجديد لزرار Add to cart
    private By addToCartButton = By.cssSelector("button.btn.btn-default.cart");

    // ===== Actions =====

    public void setQuantity(int quantity) {
        String q = String.valueOf(quantity);
        driver.findElement(quantityInput).clear();
        driver.findElement(quantityInput).sendKeys(q);
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }
}
