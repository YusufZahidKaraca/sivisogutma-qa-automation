
//////////////////////// 17-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.ArrayList;

public class OnYedi {

    WebDriver driver;

    //=====================================================================================
    // HOOK: Senaryo sonunda Firefox'u güvenli bir şekilde kapatır
    //=====================================================================================
    @After("@17.")
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println("17. Senaryo için Firefox güvenli bir şekilde kapatıldı.");

            } catch (Exception e) {

                System.out.println("Tarayıcı kapatılırken hata oluştu: " + e.getMessage());

            } finally {

                driver = null;
            }
        }
    }

    //=====================================================================================
    // Firefox Kurulum
    //=====================================================================================
    @Given("17. I navigate to {string}")
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

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();

        driver.get(url);

        // Çerez popup
        try {

            WebElement cookieAccept = driver.findElement(
                    By.id("cn-accept-cookie")
            );

            cookieAccept.click();

            System.out.println("Firefox: Çerezler kabul edildi.");

        } catch (Exception e) {

            System.out.println("Çerez banner'ı görünmedi.");
        }
    }

    //=====================================================================================
    // Ürün Arama
    //=====================================================================================
    @When("17. I search for product code {string}")
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
    @When("17. I click the add to cart button")
    public void clickAddToCart() {

        WebElement addToCartBtn = driver.findElement(
                By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")
        );

        addToCartBtn.click();

        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    //=====================================================================================
    // Sekme Yönetimi
    //=====================================================================================
    @And("17. I open a new tab and go to the cart page")
    public void i_open_a_new_tab_and_go_to_the_cart_page() {

        // Yeni sekme aç
        ((JavascriptExecutor) driver).executeScript("window.open()");

        ArrayList<String> tabs = new ArrayList<>(
                driver.getWindowHandles()
        );

        // İlk sekmeyi kapat
        driver.switchTo().window(tabs.get(0));

        System.out.println("Firefox: Eski sekme kapatılıyor...");

        driver.close();

        // Yeni sekmeye geç
        driver.switchTo().window(tabs.get(1));

        driver.get("https://sivisogutma.com/cart/");

        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    //=====================================================================================
    // Kontrol
    //=====================================================================================
    @Then("17. The product should be added to the cart successfully")
    public void check_cart_persistence() {

        try {

            System.out.println(
                    "Firefox DURUM: Yeni sekmede sepet kontrol ediliyor..."
            );

            // Sepet tablosu kontrolü
            if (driver.findElements(By.className("shop_table")).size() > 0) {

                System.out.println(
                        "SONUÇ: Başarılı. Ürünler yeni sekmede korunuyor."
                );

            } else {

                System.out.println(
                        "SONUÇ: Başarısız. Sepet verisi taşınamadı."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Firefox Hata: Sepet kontrolü sırasında hata: "
                            + e.getMessage()
            );
        }
    }
}
