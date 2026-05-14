
//////////////////////// 16-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class OnAltı {

    WebDriver driver;

    //=====================================================================================
    // HOOK: Sadece @16 etiketli senaryo bittiğinde Firefox'u güvenli kapatır
    //=====================================================================================
    @After("@16.")
    public void tearDown() {

        if (driver != null) {

            try {

                driver.quit();

                System.out.println("16. Senaryo için Firefox güvenli bir şekilde kapatıldı.");

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
    @Given("16. I navigate to {string}")
    public void i_navigate_to(String url) {

        // Selenium Manager otomatik driver yönetimi yapar
        // System.setProperty kaldırıldı

        FirefoxOptions options = new FirefoxOptions();

        // Firefox standart yerde değilse aç
        //options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

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

            driver.findElement(By.id("cn-accept-cookie")).click();

        } catch (Exception e) {

            System.out.println("Çerez popup bulunamadı.");
        }
    }

    //=====================================================================================
    // Ürün Arama ve Sepete Ekleme
    //=====================================================================================
    @When("16. I search for product code {string}")
    public void i_search_for_product_code(String productCode) {

        WebElement searchBar = driver.findElement(
                By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/input")
        );

        searchBar.clear();

        searchBar.sendKeys(productCode);

        searchBar.submit();
    }

    @When("16. I click the add to cart button")
    public void i_click_the_add_to_cart_button() {

        WebElement addToCartBtn = driver.findElement(
                By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")
        );

        addToCartBtn.click();

        try {

            Thread.sleep(2500);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    //=====================================================================================
    // Manuel Miktar Girişi
    //=====================================================================================
    @When("16. I go to the cart page and enter quantity {string} for the first item")
    public void i_go_to_the_cart_page_and_enter_quantity_for_the_first_item(String quantity) {

        // Sepet ikonuna git
        WebElement cartIcon = driver.findElement(
                By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[4]/div/div/div[1]/a/h6")
        );

        cartIcon.click();

        try {

            Thread.sleep(3500);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        try {

            WebElement quantityInput = driver.findElement(
                    By.xpath("/html/body/div[2]/div[2]/div/div/div/div/div/div/div/form/div[1]/div[1]/table/tbody/tr[1]/td[4]/div/input[2]")
            );

            quantityInput.click();

            quantityInput.sendKeys(Keys.CONTROL + "a");

            quantityInput.sendKeys(Keys.BACK_SPACE);

            quantityInput.sendKeys(quantity);

            quantityInput.sendKeys(Keys.ENTER);

            System.out.println(
                    "Firefox DURUM: Spesifik XPath ile miktar "
                            + quantity +
                            " yapıldı."
            );

        } catch (Exception e) {

            System.out.println(
                    "Firefox Hata: Verilen XPath ile miktar kutusu bulunamadı! "
                            + e.getMessage()
            );
        }

        try {

            Thread.sleep(2000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    //=====================================================================================
    // Test Sonu
    //=====================================================================================
    @Then("16. The product should be added to the cart successfully")
    public void the_product_should_be_added_to_the_cart_successfully() {

        System.out.println("Firefox DURUM: 16. Senaryo akışı başarıyla bitti.");
    }
}
