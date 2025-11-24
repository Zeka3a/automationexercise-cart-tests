package Zeka;

import Post.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TestCart extends BaseTests {

    // ===== Helpers =====

    // نفتح صفحة الكارت مباشرة
    private void openCartPage() {
        driver.get("https://automationexercise.com/view_cart");
        wait.until(ExpectedConditions.urlContains("view_cart"));
    }

    // نفرّغ الكارت من أي منتجات
    private void ensureCartIsEmpty() {
        openCartPage();

        while (true) {
            List<WebElement> deleteButtons =
                    driver.findElements(By.cssSelector("a.cart_quantity_delete"));

            if (deleteButtons.isEmpty())
                break;

            WebElement btn = deleteButtons.get(0);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
            } catch (Exception e) {
                btn.click();
            }

            wait.until(ExpectedConditions.stalenessOf(btn));
        }
    }

    // تجهيز منتج واحد في الكارت بكمية معينة
    private void prepareSingleItemInCart(int quantity) {

        ensureCartIsEmpty();

        // نروح مباشرة لصفحة المنتجات
        driver.get("https://automationexercise.com/products");
        wait.until(ExpectedConditions.urlContains("products"));

        // نفتح تفاصيل أول منتج
        productsPage.openFirstProductDetails();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quantity")));

        // نغيّر الكمية
        productDetailPage.setQuantity(quantity);

        // Add to cart
        productDetailPage.clickAddToCart();

        // View Cart من الـ popup
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//u[.='View Cart']/parent::a")));
        productsPage.clickViewCartFromPopup();

        // نتأكد إننا في صفحة الكارت
        wait.until(ExpectedConditions.urlContains("view_cart"));
    }

    private int getCartRowsCount() {
        return cartPage.getRowsCount();
    }

    // ===== Tests =====

    @Test(priority = 1)
    public void verifyCartPa3() {
        openCartPage();

        String currentUrl = cartPage.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("view_cart"),
                "Expected URL to contain 'view_cart' but was: " + currentUrl
        );
    }

    @Test(priority = 2)
    public void verifyEmpty() {
        ensureCartIsEmpty();

        int rows = getCartRowsCount();
        Assert.assertEquals(rows, 0, "Cart should be empty (0 rows in table).");
    }

    @Test(priority = 3)
    public void addFirstPro() {

        prepareSingleItemInCart(1);

        int rows = getCartRowsCount();

        System.out.println("DEBUG addFirstPro: rows in cart = " + rows);

        Assert.assertTrue(rows >= 1,
                "After adding first product, cart should have at least 1 row.");
    }

    @Test(priority = 4)
    public void removeFirst() {

        prepareSingleItemInCart(1);

        cartPage.removeFirstProduct();

        int rows = getCartRowsCount();

        System.out.println("DEBUG removeFirst: rows in cart = " + rows);

        Assert.assertEquals(rows, 0,
                "After removing the product, cart should have 0 rows.");
    }

    @Test(priority = 5)
    public void changeQuan() {

        int desiredQty = 3;

        prepareSingleItemInCart(desiredQty);

        int rows = getCartRowsCount();
        int actualQty = cartPage.getFirstProductQuantity();

        System.out.println("DEBUG changeQuan: rows = " + rows +
                ", qty = " + actualQty);

        Assert.assertTrue(rows >= 1,
                "After changing quantity, cart should still have at least 1 row.");
        Assert.assertTrue(actualQty > 0,
                "Quantity in cart should be > 0.");
    }
}
