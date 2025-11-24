package Cart;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emptyCartMessage =
            By.xpath("//*[contains(.,'Cart is empty')]");

    private final By firstProductName =
            By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[2]/h4/a");

    private final By firstProductPrice =
            By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[3]/p");

    private final By firstProductQuantity =
            By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[4]/button");

    private final By firstProductTotal =
            By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[5]/p");

    // أزرار الحذف لكل الصفوف
    private final By deleteButtons = By.cssSelector("a.cart_quantity_delete");

    // كل الصفوف في جدول الكارت
    private final By cartRows = By.cssSelector("#cart_info_table tbody tr");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private int parsePriceToInt(String text) {
        String digits = text.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return 0;
        return Integer.parseInt(digits);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isEmptyCartMessageDisplayed() {
        try {
            WebElement msg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(emptyCartMessage));
            return msg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstProductName() {
        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(firstProductName))
                .getText().trim();
    }

    public String getFirstProductPrice() {
        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(firstProductPrice))
                .getText().trim();
    }

    public int getFirstProductPriceValue() {
        return parsePriceToInt(getFirstProductPrice());
    }

    public int getFirstProductQuantity() {
        String q = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(firstProductQuantity))
                .getText().trim();
        return Integer.parseInt(q);
    }

    public String getFirstProductTotal() {
        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(firstProductTotal))
                .getText().trim();
    }

    public int getFirstProductTotalValue() {
        return parsePriceToInt(getFirstProductTotal());
    }

    // عدد الصفوف في الكارت
    public int getRowsCount() {
        List<WebElement> rows = driver.findElements(cartRows);
        return rows.size();
    }

    // حذف أول منتج مع معالجة ElementClickInterceptedException
    public void removeFirstProduct() {
        List<WebElement> buttons = driver.findElements(deleteButtons);
        if (buttons.isEmpty()) {
            return;
        }

        WebElement btn = buttons.get(0);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", btn);
        }

        // ننتظر لحد ما الصف يختفي من الـ DOM
        wait.until(ExpectedConditions.stalenessOf(btn));
    }
}
