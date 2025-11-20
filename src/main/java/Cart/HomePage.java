package Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    // ===== Constructor =====
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ===== Locators =====

    // لينك الكارت في الهيدر
    private By cartLink = By.cssSelector("a[href='/view_cart']");

    // لينك صفحة الـ Products في الهيدر
    private By productsLink = By.cssSelector("a[href='/products']");

    // ===== Actions =====

    public void clickCart() {
        driver.findElement(cartLink).click();
    }

    public void clickProducts() {
        driver.findElement(productsLink).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
