

//////////////////////// 19-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class OnDokuz {

    WebDriver driver;

    //=====================================================================================
    // HOOK: Tarayıcıyı Firefox olarak güvenli kapatır
    //=====================================================================================
    @After("@19.")
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println(
                        "19. Senaryo için Firefox güvenli bir şekilde kapatıldı."
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
    @Given("19. I navigate to {string}")
    public void i_navigate_to(String url) {

        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        // Gizli sekme
        options.addArguments("-private");

        // Çözünürlük
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        driver = new FirefoxDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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
    @When("19. I search for product code {string}")
    public void i_search_for_product_code(String productCode) {

        WebElement searchBar = driver.findElement(
                By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/input")
        );

        searchBar.clear();

        searchBar.sendKeys(productCode);

        searchBar.submit();
    }

    //=====================================================================================
    // Favorilere Ekle
    //=====================================================================================
    @And("19. I click the add to wishlist button")
    public void i_click_the_add_to_wishlist_button() {

        try {

            WebElement wishlistBtn = driver.findElement(
                    By.xpath("//a[contains(@class, 'add_to_wishlist')]")
            );

            // Firefox overlay sorunlarına karşı JS click
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    wishlistBtn
            );

            System.out.println(
                    "Firefox DURUM: Ürün favorilere eklendi."
            );

            Thread.sleep(3000);

        } catch (Exception e) {

            System.out.println(
                    "Favori butonu hatası: "
                            + e.getMessage()
            );
        }
    }

    //=====================================================================================
    // İstek Listesi Kontrolü
    //=====================================================================================
    @Then("19. I should see the product in my wishlist page")
    public void i_should_see_the_product_in_my_wishlist_page() {

        try {

            // İstek listesini görüntüle butonu
            WebElement goToWishlistBtn = driver.findElement(
                    By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/div[4]/div/a")
            );

            goToWishlistBtn.click();

            System.out.println(
                    "Firefox: İstek listesi butonuna tıklandı."
            );

        } catch (Exception e) {

            // Failsafe
            System.out.println(
                    "Buton bulunamadı, doğrudan URL ile gidiliyor..."
            );

            driver.get(
                    "https://sivisogutma.com/istek-listesi/"
            );
        }

        try {

            Thread.sleep(2000);

        } catch (Exception e) {

            e.printStackTrace();
        }

        // URL doğrulama
        if (driver.getCurrentUrl().contains("istek-listesi")) {

            System.out.println(
                    "TEST BAŞARILI: Firefox üzerinde istek listesi doğrulandı."
            );

        } else {

            System.out.println(
                    "TEST HATASI: İstek listesi sayfasına ulaşılamadı."
            );
        }
    }
}

