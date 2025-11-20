package Zeka;

import Post.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCart extends BaseTests {

    // Helper: فتح صفحة الكارت من الهوم
    private void openCartPage() {
        homePage.clickCart();
        wait.until(ExpectedConditions.urlContains("view_cart"));
    }

    // Helper: نتأكد إن الكارت فاضي قبل أي تست يعتمد على حالته
    private void ensureCartIsEmpty() {
        // افتح صفحة الكارت
        openCartPage();

        // طول ما فيه زر delete في صفحة الكارت → امسحه
        while (!driver.findElements(By.cssSelector("a.cart_quantity_delete")).isEmpty()) {
            driver.findElement(By.cssSelector("a.cart_quantity_delete")).click();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // مفيش حاجة نعملها هنا
            }
        }
    }

    // Helper: إضافة أول منتج للكارت ثم فتح صفحة الكارت (مع تنظيف قبلها)
    private void addFirstProductToCartAndOpenCart() {

        // نضمن أن الكارت فاضي
        ensureCartIsEmpty();

        // نروح لصفحة الـ Products
        homePage.clickProducts();
        wait.until(ExpectedConditions.urlContains("products"));

        // نضيف أول منتج
        productsPage.addFirstProductToCart();

        // نستنى الـ Popup وندوس View Cart
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//u[.='View Cart']/parent::a")));
        productsPage.clickViewCartFromPopup();

        // نستنى لحد ما نبقى في صفحة الكارت
        wait.until(ExpectedConditions.urlContains("view_cart"));
    }

    // ----------------- Tests -----------------

    // Test 1: URL بتاع الكارت
    @Test(priority = 1)
    public void verifyCartPageUrl() {
        openCartPage();

        String currentUrl = cartPage.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("view_cart"),
                "Expected URL to contain 'view_cart' but was: " + currentUrl
        );
    }

    // Test 2: رسالة الكارت الفاضي
    @Test(priority = 2)
    public void verifyEmptyCartMessage() {
        ensureCartIsEmpty();

        boolean isEmptyMessageShown = cartPage.isEmptyCartMessageDisplayed();
        Assert.assertTrue(
                isEmptyMessageShown,
                "Expected 'Cart is empty!' message to be displayed."
        );
    }

    // Test 3: إضافة أول منتج للكارت والتأكد من البيانات الأساسية
    @Test(priority = 3)
    public void addFirstProductToCartAndVerifyDetails() {

        addFirstProductToCartAndOpenCart();

        String name = cartPage.getFirstProductName();
        String price = cartPage.getFirstProductPrice();
        int quantity = cartPage.getFirstProductQuantity();
        String total = cartPage.getFirstProductTotal();

        System.out.println("DEBUG addFirst: name=" + name + ", price=" + price +
                ", qty=" + quantity + ", total=" + total);

        Assert.assertEquals(name, "Blue Top", "Product name is not as expected");
        Assert.assertEquals(price, "Rs. 500", "Product price is not as expected");
        Assert.assertEquals(quantity, 1, "Quantity should be 1");
        Assert.assertEquals(total, "Rs. 500", "Total price is not as expected");
    }

    // Test 4: Remove from Cart (حذف أول منتج والتأكد إن الكارت فاضي)
    @Test(priority = 4)
    public void removeFirstProductFromCart() {

        // نضيف منتج واحد ونفتح الكارت
        addFirstProductToCartAndOpenCart();

        // نحذف أول منتج
        cartPage.removeFirstProduct();

        // نستخدم نفس الـ Helper اللي متأكد إن الكارت فاضي
        ensureCartIsEmpty();

        Assert.assertTrue(
                cartPage.isEmptyCartMessageDisplayed(),
                "Expected cart to be empty after removing the product."
        );
    }

    // Test 5: Change Quantity & Verify Total
    @Test(priority = 5)
    public void changeQuantityAndVerifyTotal() {

        // نبدأ بكارت فاضي
        ensureCartIsEmpty();

        // 1) نروح لصفحة Products
        homePage.clickProducts();
        wait.until(ExpectedConditions.urlContains("products"));

        // 2) نفتح تفاصيل أول منتج
        productsPage.openFirstProductDetails();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quantity")));

        // 3) نغير الكمية مثلاً لـ 3 من صفحة التفاصيل
        int desiredQty = 3;
        productDetailPage.setQuantity(desiredQty);
        productDetailPage.clickAddToCart();

        // 4) Popup → View Cart
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//u[.='View Cart']/parent::a")));
        productsPage.clickViewCartFromPopup();

        // 5) نستنى صفحة الكارت
        wait.until(ExpectedConditions.urlContains("view_cart"));

        // 6) نقرأ الكمية والسعر والـ Total من الكارت
        int actualQty = cartPage.getFirstProductQuantity();
        int unitPrice = cartPage.getFirstProductPriceValue();
        int total = cartPage.getFirstProductTotalValue();

        System.out.println("DEBUG changeQty: qty=" + actualQty +
                ", unitPrice=" + unitPrice + ", total=" + total);

        // 7) Assertions
        Assert.assertEquals(actualQty, desiredQty, "Quantity in cart is not as expected");
        Assert.assertEquals(total, unitPrice * desiredQty, "Total price is not correct");
    }
}
