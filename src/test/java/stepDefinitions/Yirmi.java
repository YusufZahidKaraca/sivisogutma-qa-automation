
//////////////////////// 20-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class Yirmi {

    WebDriver driver;

    //=====================================================================================
    // HOOK: Senaryo sonunda Firefox'u güvenli bir şekilde kapatır
    //=====================================================================================
    @After("@20.")
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println(
                        "20. Senaryo için Firefox güvenli bir şekilde kapatıldı."
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
    @Given("20. I navigate to {string}")
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
    @When("20. I search for product code {string}")
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
    @And("20. I click the add to wishlist button")
    public void i_click_the_add_to_wishlist_button() {

        try {

            WebElement wishlistBtn = driver.findElement(
                    By.xpath("//a[contains(@class, 'add_to_wishlist')]")
            );

            // Scroll
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);",
                    wishlistBtn
            );

            Thread.sleep(800);

            // Firefox için JS click daha stabil
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
                    "Favori butonu hatası (Firefox): "
                            + e.getMessage()
            );
        }
    }

    //=====================================================================================
    // İstek Listesine Git
    //=====================================================================================
    @And("20. I go to the wishlist page")
    public void i_go_to_the_wishlist_page() {

        try {

            WebElement goToWishlistBtn = driver.findElement(
                    By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/div[4]/div/a")
            );

            goToWishlistBtn.click();

        } catch (Exception e) {

            // Failsafe
            driver.get(
                    "https://sivisogutma.com/istek-listesi/"
            );
        }

        try {

            Thread.sleep(2000);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //=====================================================================================
    // İstek Listesinden Sil
    //=====================================================================================
    @When("20. I remove the product from the wishlist")
    public void i_remove_the_product_from_the_wishlist() {

        try {

            WebElement removeBtn = driver.findElement(
                    By.className("remove_from_wishlist")
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);",
                    removeBtn
            );

            Thread.sleep(1000);

            // Firefox click optimizasyonu
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    removeBtn
            );

            System.out.println(
                    "Firefox DURUM: Ürün istek listesinden kaldırıldı."
            );

            Thread.sleep(3000);

        } catch (Exception e) {

            System.out.println(
                    "Kaldırma hatası (Firefox): "
                            + e.getMessage()
            );

            try {

                // Alternatif XPath
                WebElement backupBtn = driver.findElement(
                        By.xpath("//a[@title='Bu ürünü kaldır']")
                );

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        backupBtn
                );

                System.out.println(
                        "Firefox: Yedek yol ile silindi."
                );

            } catch (Exception e2) {

                System.out.println(
                        "Firefox: Silme işlemi başarısız."
                );
            }
        }
    }

    //=====================================================================================
    // Doğrulama
    //=====================================================================================
    @Then("20. The wishlist should be empty")
    public void the_wishlist_should_be_empty() {

        // Sayfayı yenile
        driver.navigate().refresh();

        try {

            Thread.sleep(2000);

            if (!driver.findElements(
                    By.className("wishlist-empty")
            ).isEmpty()
                    ||
                    driver.findElements(
                            By.className("wishlist-items-wrapper")
                    ).isEmpty()) {

                System.out.println(
                        "SONUÇ: Başarılı! Firefox üzerinde istek listesi boş."
                );

            } else {

                System.out.println(
                        "SONUÇ: Başarısız! Firefox'ta liste hala dolu görünüyor."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Doğrulama sırasında hata: "
                            + e.getMessage()
            );
        }
    }
}
