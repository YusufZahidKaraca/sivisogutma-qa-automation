
//////////////////////// 34-fire fox ////////////////////////

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

public class OtuzDört {

    WebDriver driver;
    WebDriverWait wait;
    //=====================================================================================
    // HOOK
    //=====================================================================================
    @After("@34.")
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println(
                        "34. Senaryo (Logo Testi) başarıyla tamamlandı ve kapatıldı."
                );

            } catch (Exception e) {

                System.out.println(
                        "Tarayıcı kapatılırken hata oluştu: "
                                + e.getMessage()
                );

            } finally {

                driver = null;
            }
        }
    }

    //=====================================================================================
    // Firefox Kurulum
    //=====================================================================================
    @Given("34. I navigate to {string}")
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

    //=====================================================================================
    // Logo tıklama
    //=====================================================================================
    @When("34. I click on the header logo")
    public void i_click_on_the_header_logo() {

        try {

            WebElement logo = driver.findElement(
                    By.xpath("//header//a[contains(@class, 'logo')] | //*[@id='header']//img")
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    logo
            );

            Thread.sleep(2000);

        } catch (Exception e) {

            System.out.println(
                    "Logo bulunamadı veya tıklanamadı: "
                            + e.getMessage()
            );
        }
    }

    //=====================================================================================
    // URL doğrulama
    //=====================================================================================
    @Then("34. I should be redirected to the home page {string}")
    public void verify_redirection(String expectedUrl) {

        String currentUrl = driver.getCurrentUrl();

        String normalizedCurrent = currentUrl.replaceAll("/$", "");
        String normalizedExpected = expectedUrl.replaceAll("/$", "");

        System.out.println(
                "Kontrol Ediliyor: " + normalizedCurrent
        );

        Assert.assertEquals(
                normalizedCurrent,
                normalizedExpected,
                "Logo hatalı URL'ye yönlendirdi!"
        );
    }
}
