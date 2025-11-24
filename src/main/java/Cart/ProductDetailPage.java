package Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By quantityInput   = By.id("quantity");
    private final By addToCartButton = By.cssSelector("button.btn.btn-default.cart");

    public ProductDetailPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void setQuantity(int qty) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(quantityInput));
        input.clear();
        input.sendKeys(String.valueOf(qty));
    }

    public void clickAddToCart() {
        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(addToCartButton));
        btn.click();
    }
}
