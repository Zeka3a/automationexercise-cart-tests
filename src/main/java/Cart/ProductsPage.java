package Cart;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    // كونتينر المنتجات – علشان نتأكد إن الصفحة اتحمّلت
    private final By productsContainer = By.cssSelector(".features_items");

    // أول View Product (Blue Top عادةً)
    private final By firstViewProductByHref =
            By.cssSelector("a[href='/product_details/1']");

    private final By firstViewProductByText =
            By.xpath("(//a[contains(normalize-space(),'View Product')])[1]");

    // زر View Cart في الـ popup
    private final By popupViewCartButton =
            By.xpath("//div[contains(@class,'modal-content')]//a[.//u[normalize-space()='View Cart']]");

    public ProductsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.js = (JavascriptExecutor) driver;
    }

    // فتح صفحة تفاصيل أول منتج
    public void openFirstProductDetails() {
        // نتأكد إن المنتجات ظهرت
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsContainer));

        WebElement viewLink;

        try {
            // نجرب بالـ href الأول
            viewLink = wait.until(
                    ExpectedConditions.elementToBeClickable(firstViewProductByHref));
        } catch (TimeoutException e) {
            // fallback بالنص لو لسبب ما الـ href ما اشتغلش
            viewLink = wait.until(
                    ExpectedConditions.elementToBeClickable(firstViewProductByText));
        }

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", viewLink);
        js.executeScript("arguments[0].click();", viewLink);
    }

    // الضغط على "View Cart" في الـ popup
    public void clickViewCartFromPopup() {
        WebElement viewCart = wait.until(
                ExpectedConditions.elementToBeClickable(popupViewCartButton));

        js.executeScript("arguments[0].click();", viewCart);
    }
}
