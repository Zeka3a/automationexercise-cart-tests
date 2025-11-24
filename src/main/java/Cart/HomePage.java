package Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // نستعمل href عشان يبقى ثابت
    private final By productsLink = By.xpath("//a[@href='/products']");
    private final By cartLink     = By.xpath("//a[@href='/view_cart']");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickProducts() {
        driver.findElement(productsLink).click();
    }

    public void clickCart() {
        driver.findElement(cartLink).click();
    }
}
