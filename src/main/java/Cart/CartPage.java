package Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // ===== Locators =====

    // رسالة الكارت الفاضي
    private By emptyCartMessage = By.xpath("//*[contains(text(),'Cart is empty')]");

    // أول منتج في جدول الكارت
    private By firstProductName = By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[2]/h4/a");
    private By firstProductPrice = By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[3]/p");
    private By firstProductQuantity = By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[4]/button");
    private By firstProductTotal = By.xpath("//table[@id='cart_info_table']//tbody/tr[1]/td[5]/p");

    // زر حذف أول منتج
    private By firstProductDeleteButton = By.cssSelector("a.cart_quantity_delete");

    // ===== Helpers =====

    // نتأكد هل رسالة الكارت الفاضي موجودة ولا لأ
    public boolean isEmptyCartMessageDisplayed() {
        return !driver.findElements(emptyCartMessage).isEmpty();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // بيانات أول منتج (Strings)
    public String getFirstProductName() {
        return driver.findElement(firstProductName).getText();
    }

    public String getFirstProductPrice() {
        return driver.findElement(firstProductPrice).getText();
    }

    public int getFirstProductQuantity() {
        String qtyText = driver.findElement(firstProductQuantity).getText().trim();
        return Integer.parseInt(qtyText);
    }

    public String getFirstProductTotal() {
        return driver.findElement(firstProductTotal).getText();
    }

    // تحويل السعر والـ Total لأرقام علشان نعرف نحسب
    public int getFirstProductPriceValue() {
        String priceText = getFirstProductPrice();      // مثال: "Rs. 500"
        String digits = priceText.replaceAll("[^0-9]", "");
        return Integer.parseInt(digits);
    }

    public int getFirstProductTotalValue() {
        String totalText = getFirstProductTotal();      // مثال: "Rs. 1500"
        String digits = totalText.replaceAll("[^0-9]", "");
        return Integer.parseInt(digits);
    }

    // حذف أول منتج من الكارت
    public void removeFirstProduct() {
        driver.findElement(firstProductDeleteButton).click();
    }
}
