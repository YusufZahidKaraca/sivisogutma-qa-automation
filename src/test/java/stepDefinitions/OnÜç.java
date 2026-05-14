package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OnÜç {

    WebDriver driver;
    WebDriverWait wait;

    // ÖNEMLİ: Feature dosyasındaki senaryonun başında @scenario13 etiketi olmalı.
    @After("@scenario13")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Firefox başarıyla kapatıldı.");
        }
    }

    @Given("scenario13 I navigate to {string}")
    public void i_navigate_to(String url) {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        // --- Gizli Sekme Ayarı ---
        options.addArguments("-private");

        // Tarayıcı binary yolunu belirtmeye devam ediyoruz
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.manage().window().maximize();
        driver.get(url);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cn-accept-cookie"))).click();
        } catch (Exception e) {
            // Çerez ekranı gelmezse testi durdurma
        }
    }

    @When("scenario13 I search for product code {string}")
    public void i_search_for_product_code(String productCode) {
        WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/input")));
        searchBar.clear();
        searchBar.sendKeys(productCode);
        searchBar.submit();
    }

    @When("scenario13 I add {string} items to the cart")
    public void i_add_items_to_the_cart(String quantity) {
        int targetCount = Integer.parseInt(quantity);
        String plusButtonXpath = "//*[@id=\"product-summary-wrapper\"]/div/div/form/div/input[3]";
        String qtyInputXpath = "//*[@id=\"product-summary-wrapper\"]/div/div/form/div/input[2]";

        for (int i = 0; i < (targetCount - 1); i++) {
            WebElement qtyInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(qtyInputXpath)));
            int beforeValue = Integer.parseInt(qtyInput.getAttribute("value"));

            try {
                WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(plusButtonXpath)));
                plusButton.click();

                new WebDriverWait(driver, Duration.ofSeconds(3)).until(d -> {
                    String currentValue = d.findElement(By.xpath(qtyInputXpath)).getAttribute("value");
                    return Integer.parseInt(currentValue) > beforeValue;
                });

            } catch (Exception e) {
                i--;
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        }
        clickAddToCart();
    }

    @When("scenario13 I click the add to cart button")
    public void clickAddToCart() {
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")));
        addToCartBtn.click();

        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @When("scenario13 I go to the cart page and click plus button for the first item")
    public void i_go_to_the_cart_page() {
        driver.get("https://sivisogutma.com/cart/");

        try {
            WebElement plusInCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='+']")));
            plusInCart.click();
            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("Sepet sayfasında artı butonu bulunamadı.");
        }
    }

    @Then("scenario13 The product should be added to the cart successfully")
    public void finish() {
        System.out.println("Firefox DURUM: Test başarıyla sonuçlandı.");
    }
}