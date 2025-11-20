package Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage {

    private WebDriver driver;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    // ===== Locators =====

    // زر Add to cart لأول منتج في صفحة الـ Products
    private By firstProductAddToCartButton =
            By.xpath("(//a[contains(text(),'Add to cart')])[1]");

    // زر View Cart اللي في الـ Popup
    private By viewCartButton =
            By.xpath("//u[.='View Cart']/parent::a");

    // زر View Product لأول منتج (علشان نغير الكمية من صفحة التفاصيل)
    private By firstProductViewButton =
            By.xpath("(//a[contains(text(),'View Product')])[1]");

    // ===== Actions =====

    public void addFirstProductToCart() {
        driver.findElement(firstProductAddToCartButton).click();
    }

    public void clickViewCartFromPopup() {
        driver.findElement(viewCartButton).click();
    }

    public void openFirstProductDetails() {
        driver.findElement(firstProductViewButton).click();
    }
}
