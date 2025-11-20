package Post;

import Cart.CartPage;
import Cart.HomePage;
import Cart.ProductsPage;
import Cart.ProductDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTests {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Page Objects
    protected HomePage homePage;
    protected CartPage cartPage;
    protected ProductsPage productsPage;
    protected ProductDetailPage productDetailPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        productsPage = new ProductsPage(driver);
        productDetailPage = new ProductDetailPage(driver);
    }

    @BeforeMethod
    public void goHome() {
        driver.get("https://automationexercise.com/");
    }

    @AfterClass
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
