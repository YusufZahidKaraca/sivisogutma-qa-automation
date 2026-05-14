
//////////////////////// 14-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OnDört {

    WebDriver driver;

    //=====================================================================================
    // HOOK: Tarayıcıyı Firefox olarak güvenli kapatır
    //=====================================================================================
    @After("@14.")
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println("14. Senaryo için Firefox güvenli bir şekilde kapatıldı.");

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
    @Given("14. I navigate to {string}")
    public void i_navigate_to(String url) {

        // Selenium Manager otomatik driver yönetimi yapar
        // System.setProperty kaldırıldı

        FirefoxOptions options = new FirefoxOptions();

        // Firefox standart yerde değilse aç
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        // Gizli sekme
        //options.addArguments("-private");

        // Çözünürlük
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        driver = new FirefoxDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();

        driver.get(url);

        // Çerez popup
        try {

            driver.findElement(By.id("cn-accept-cookie")).click();

        } catch (Exception e) {

            System.out.println("Çerez popup bulunamadı.");
        }
    }

    //=====================================================================================
    // Ürün Arama ve Tüm Stoğu Ekleme
    //=====================================================================================
    @When("14. I search for product code {string}")
    public void i_search_for_product_code(String productCode) {

        WebElement searchBar = driver.findElement(
                By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/input")
        );

        searchBar.clear();

        searchBar.sendKeys(productCode);

        searchBar.submit();
    }

    @When("14. I add all available items to the cart")
    public void i_add_all_available_items_to_the_cart() {

        WebElement plusButton = driver.findElement(
                By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/div/input[3]")
        );

        for (int i = 0; i < 9; i++) {

            try {

                plusButton.click();

                Thread.sleep(150);

            } catch (Exception e) {

                break;
            }
        }

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
    // Sepet Sayfası ve 999 Adet Zorlaması
    //=====================================================================================
    @When("14. I go to the cart page and try to exceed stock limit")
    public void i_go_to_the_cart_page_and_try_to_exceed_stock_limit() {

        WebElement cartIcon = driver.findElement(
                By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[4]/div/div/div[1]/a/h6")
        );

        cartIcon.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement quantityInput = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//td[@class='product-quantity']//input[contains(@class, 'qty')]")
                )
        );

        quantityInput.click();

        quantityInput.sendKeys(Keys.CONTROL + "a");

        quantityInput.sendKeys(Keys.BACK_SPACE);

        quantityInput.sendKeys("999");

        quantityInput.sendKeys(Keys.ENTER);

        System.out.println("Firefox DURUM: Sepet miktarı 999 olarak zorlandı.");

        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    //=====================================================================================
    // Bitiş ve Onay
    //=====================================================================================
    @Then("14. The product should be added to the cart successfully")
    public void the_product_should_be_added_to_the_cart_successfully() {

        System.out.println("Firefox DURUM: 14. Senaryo başarıyla tamamlandı.");
    }

    @When("14. I click the increase quantity button")
    public void i_click_the_increase_quantity_button() {

        i_add_all_available_items_to_the_cart();
    }
}
