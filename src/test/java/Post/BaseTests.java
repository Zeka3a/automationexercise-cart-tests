package Post;

import Cart.CartPage;
import Cart.HomePage;
import Cart.ProductDetailPage;
import Cart.ProductsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTests {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected HomePage homePage;
    protected ProductsPage productsPage;
    protected ProductDetailPage productDetailPage;
    protected CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // افتح الهوم
        driver.get("https://automationexercise.com/");

        // Page Objects
        homePage = new HomePage(driver, wait);
        productsPage = new ProductsPage(driver, wait);
        productDetailPage = new ProductDetailPage(driver, wait);
        cartPage = new CartPage(driver, wait);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
