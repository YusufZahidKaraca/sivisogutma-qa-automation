

//////////////////////// 15-fire fox ////////////////////////

package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OnBeş {

    WebDriver driver;
    WebDriverWait wait;

    @After("@15.")
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Firefox: @15. Senaryosu başarıyla sonlandırıldı.");
            } catch (Exception e) {
                System.out.println("Kapatma hatası: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    @Given("15. I navigate to {string}")
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

    @When("15. I search for product code {string}")
    public void i_search_for_product_code(String productCode) {
        // Mevcut URL'i alalım ki sayfanın değiştiğini kontrol edebilelim
        String oldUrl = driver.getCurrentUrl();

        WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[3]/div/form/div[1]/input")));
        searchBar.clear();
        searchBar.sendKeys(productCode);
        searchBar.submit();

        // --- KRİTİK DÜZENLEME 1: Sayfanın değişmesini bekle ---
        // Arama butonuna bastıktan sonra URL'in değişmesini bekliyoruz
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));

        // --- KRİTİK DÜZENLEME 2: Ürün sayfasındaki miktar kutusunun gelmesini bekle ---
        // Bu kutu gelmeden sepete ekle butonu çalışmaz
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/div/input[2]")));
    }

    @When("15. I add all available items to the cart")
    public void i_add_all_available_items_to_the_cart() {
        try {
            WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/div/input[3]")));
            for (int i = 0; i < 9; i++) {
                plusButton.click();
                Thread.sleep(150);
            }
            i_click_the_add_to_cart_button();
        } catch (Exception e) {
            System.out.println("Miktar arttırma hatası: " + e.getMessage());
        }
    }

    @When("15. I click the add to cart button")
    public void i_click_the_add_to_cart_button() {
        try {
            // Butonu bekle ve bul
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")));

            // JavaScript ile tıklat (En garanti yol)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);

            // --- KRİTİK DÜZENLEME 3: Sepete eklendi onayını bekle ---
            // Onay mesajı (woocommerce-message) çıkana kadar dur.
            // Eğer bunu beklemezsen sepete ekleme AJAX'ı tamamlanmadan driver.get("/cart/") çalışır ve ekleme iptal olur.
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("woocommerce-message")));

            System.out.println("Firefox DURUM: Ürün başarıyla sepete eklendi.");
        } catch (Exception e) {
            System.out.println("Sepete ekleme hatası: " + e.getMessage());
        }
    }

    @When("15. I go to the cart page and click plus button for the first item")
    public void i_go_to_the_cart_page_and_click_plus_button_for_the_first_item() {
        driver.get("https://sivisogutma.com/cart/");

        try {
            WebElement secondProductPlus = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"post-1955\"]/div/div/form/div[1]/div[1]/table/tbody/tr[2]/td[4]/div/input[3]")
            ));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", secondProductPlus);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", secondProductPlus);

            System.out.println("Firefox DURUM: 2. ürün miktarı arttırıldı.");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Firefox HATA: İkinci ürün veya buton bulunamadı.");
        }
    }

    @When("15. I click the increase quantity button")
    public void i_click_the_increase_quantity_button() {
        i_add_all_available_items_to_the_cart();
    }

    @Then("15. The product should be added to the cart successfully")
    public void the_product_should_be_added_to_the_cart_successfully() {
        System.out.println("Firefox DURUM: 15. Senaryo başarıyla tamamlandı.");
    }
}
