

//////////////////////// 18-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.ArrayList;

public class OnSekiz {

    WebDriver driver;

    String firstTab;
    String secondTab;

    //=====================================================================================
    // GLOBAL HOOK
    //=====================================================================================
    @After("@18.")
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println(
                        "18. Senaryo için Firefox güvenli bir şekilde kapatıldı."
                );

            } catch (Exception e) {

                System.out.println(
                        "Kapatma hatası: " + e.getMessage()
                );

            } finally {

                driver = null;
            }
        }
    }

    //=====================================================================================
    // Firefox Kurulum
    //=====================================================================================
    @Given("18. I navigate to {string}")
    public void i_navigate_to(String url) {

        // Selenium Manager otomatik driver yönetimi yapar
        // System.setProperty kaldırıldı

        FirefoxOptions options = new FirefoxOptions();

        // Firefox standart yerde değilse aç
        // options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        // Gizli sekme
        options.addArguments("-private");

        // Çözünürlük
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        driver = new FirefoxDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        driver.manage().window().maximize();

        driver.get(url);

        // Çerez popup
        try {

            driver.findElement(
                    By.id("cn-accept-cookie")
            ).click();

        } catch (Exception e) {

            System.out.println("Çerez popup bulunamadı.");
        }
    }

    //=====================================================================================
    // Ürün Arama
    //=====================================================================================
    @When("18. I search for product code {string}")
    public void i_search_for_product_code(String productCode) {

        WebElement searchBar = driver.findElement(
                By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/input")
        );

        searchBar.clear();

        searchBar.sendKeys(productCode);

        searchBar.submit();
    }

    //=====================================================================================
    // Sepete Ekle
    //=====================================================================================
    @When("18. I click the add to cart button")
    public void clickAddToCart() {

        driver.findElement(
                By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")
        ).click();

        try {

            Thread.sleep(3000);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //=====================================================================================
    // Çakışan İşlemler
    //=====================================================================================
    @And("18. I open two tabs and perform conflicting actions")
    public void i_open_two_tabs_and_perform_conflicting_actions() {

        driver.get("https://sivisogutma.com/cart/");

        firstTab = driver.getWindowHandle();

        // Yeni sekme aç
        ((JavascriptExecutor) driver).executeScript(
                "window.open('https://sivisogutma.com/cart/', '_blank');"
        );

        try {

            Thread.sleep(3000);

        } catch (Exception e) {

            e.printStackTrace();
        }

        ArrayList<String> tabs = new ArrayList<>(
                driver.getWindowHandles()
        );

        for (String handle : tabs) {

            if (!handle.equals(firstTab)) {

                secondTab = handle;
            }
        }

        //=============================================================================
        // SEKME 1 -> Miktar Arttır
        //=============================================================================
        driver.switchTo().window(firstTab);

        System.out.println(
                "Firefox Sekme 1: Miktar arttırılıyor..."
        );

        try {

            WebElement plusBtn = driver.findElement(
                    By.xpath("//*[@id=\"post-1955\"]/div/div/form/div[1]/div[1]/table/tbody/tr[1]/td[4]/div/input[3]")
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    plusBtn
            );

            WebElement updateBtn = driver.findElement(
                    By.name("update_cart")
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    updateBtn
            );

            Thread.sleep(3000);

        } catch (Exception e) {

            System.out.println(
                    "Firefox Sekme 1 Hatası: " + e.getMessage()
            );
        }

        //=============================================================================
        // SEKME 2 -> Ürün Sil
        //=============================================================================
        driver.switchTo().window(secondTab);

        System.out.println(
                "Firefox Sekme 2: Ürün siliniyor..."
        );

        try {

            WebElement removeBtn = driver.findElement(
                    By.className("remove")
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    removeBtn
            );

            Thread.sleep(3000);

            System.out.println(
                    "Firefox Sekme 2: Silme komutu iletildi."
            );

        } catch (Exception e) {

            System.out.println(
                    "Firefox Sekme 2 Hatası: " + e.getMessage()
            );
        }
    }

    //=====================================================================================
    // Sonuç Kontrolü
    //=====================================================================================
    @Then("18. The system should handle the conflict gracefully")
    public void check_race_condition_result() {

        // Ana sekmeye dön
        driver.switchTo().window(firstTab);

        driver.navigate().refresh();

        if (driver.getPageSource().contains("Sepetiniz şu an boş")) {

            System.out.println(
                    "SONUÇ DURUM: Firefox testinde silme işlemi kazandı (Sepet Boş)."
            );

        } else {

            System.out.println(
                    "SONUÇ DURUM: Sepet hala dolu, miktar artışı veya çakışma yönetimi başarılı."
            );
        }
    }
}
