//////////////////////// 48-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class KırkSekiz {

    WebDriver driver;
    WebDriverWait wait;
    long startTime;
    long endTime;
    double durationInSeconds = 0;

    @After("@48.")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("48. Senaryo için Firefox güvenli bir şekilde kapatıldı.");
        }
    }

    @Given("48. I navigate to {string}")
    public void i_navigate_to(String url) {
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        options.addArguments("-private");

        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get(url);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cn-accept-cookie"))).click();
        } catch (Exception e) {
            System.out.println("Çerez popupı görülmedi.");
        }
    }

    // --- BURASI SARI YANAN ADIMIN ÇÖZÜMÜ ---
    @When("48. I search for product code {string}")
    public void i_search_for_product_code(String productCode) {
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/header/div[2]/div/div/div[3]/div/form/div[1]/input")));
        searchBar.clear();
        searchBar.sendKeys(productCode + Keys.ENTER);
    }

    @And("48. I measure the time to add product to cart")
    public void i_measure_time() {
        try {
            // Sepete Ekle butonunun görünmesini bekliyoruz
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@name='add-to-cart']"))); // Daha genel bir locator

            // --- ZAMAN ÖLÇÜMÜ BAŞLIYOR ---
            startTime = System.currentTimeMillis();

            // Butona tıkla
            addBtn.click();

            // Sitedeki "Sepete eklendi" mesajının (başarı bildirimi) çıkmasını bekle
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".woocommerce-message")));

            endTime = System.currentTimeMillis();
            // --- ZAMAN ÖLÇÜMÜ BİTTİ ---

            durationInSeconds = (endTime - startTime) / 1000.0;
        } catch (Exception e) {
            System.err.println("Ölçüm hatası: " + e.getMessage());
        }
    }

    @Then("48. The elapsed time should be less than 5 seconds")
    public void verify_performance() {
        System.out.println("Firefox Ölçülen Performans: " + durationInSeconds + " saniye");

        if (durationInSeconds > 0 && durationInSeconds < 5.0) {
            System.out.println("PERFORMANS DURUMU: PASS - İşlem 5 saniyenin altında tamamlandı.");
        } else {
            // Performans 5 saniyeyi geçerse testi ağır bir hatayla (Assert.fail) durdururuz
            Assert.fail("FAIL: Sepete ekleme işlemi çok yavaş! Süre: " + durationInSeconds + " sn");
        }
    }
}
