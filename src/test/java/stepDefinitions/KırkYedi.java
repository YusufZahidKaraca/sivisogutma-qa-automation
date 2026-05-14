//////////////////////// 47-fire fox ////////////////////////

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

public class KırkYedi {

    WebDriver driver;
    WebDriverWait wait;

    //=====================================================================================
    // @After HOOK: Senaryo bittiğinde tarayıcıyı temiz bir şekilde kapatır.
    //=====================================================================================
    @After("@47.")
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("47. Senaryo için Firefox güvenli şekilde kapatıldı.");
            } catch (Exception e) {
                System.out.println("Tarayıcı kapatma hatası: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    //=====================================================================================
    // GIVEN: Firefox tarayıcı ayarlarını yapar ve hedef URL'e gider.
    //=====================================================================================
    @Given("47. I navigate to {string}")
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
    // WHEN: Üst menüdeki arama çubuğunu kullanarak ürün koduyla arama yapar.
    //=====================================================================================
    @When("47. I search for product code {string}")
    public void i_search_for_product_code(String productCode) {
        // Üst Header bölümündeki ana arama input alanını (Full XPath) bulur.
        WebElement searchBar = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/div[2]/div[2]/header/div[2]/div/div/div[3]/div/form/div[1]/input")
                )
        );
        searchBar.click();
        searchBar.clear();
        searchBar.sendKeys(productCode);
        searchBar.sendKeys(Keys.ENTER); // Aramayı başlatmak için Enter tuşuna basar.
    }

    //=====================================================================================
    // AND: Ürün sayfasındaki "+" butonuna basarak stok miktarını zorlar ve sepete ekler.
    //=====================================================================================
    @And("47. I add all available items to the cart")
    public void i_add_all_available_items_to_the_cart() {
        try {
            // Ürün detay sayfasındaki miktar artırma (+) butonunu yakalar.
            WebElement plusButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/div/input[3]")
                    )
            );

            // Stokta olan miktarı artırmak için 9 kez tıklama yapar (toplamda 10 ürün hedefler).
            for (int i = 0; i < 9; i++) {
                plusButton.click();
                Thread.sleep(150); // Hızlı tıklamada sayfanın sapıtmaması için kısa bekleme.
            }

            // 'Sepete Ekle' (Add to Cart) butonuna basarak işlemi tamamlar.
            driver.findElement(By.xpath("//*[@id=\"product-summary-wrapper\"]/div/div/form/button")).click();
            Thread.sleep(2000); // Sepete ekleme animasyonunun bitmesini bekler.

        } catch (Exception e) {
            System.out.println("Firefox Sepete ekleme hatası: " + e.getMessage());
        }
    }

    //=====================================================================================
    // AND: Sepet sayfasından Ödeme (Checkout) aşamasına geçer.
    //=====================================================================================
    @And("47. I go to the cart page and try to exceed stock limit")
    public void i_go_to_the_cart_page_and_try_to_exceed_stock_limit() {
        driver.get("https://sivisogutma.com/cart/");

        try {
            // Sepet sayfasındaki 'Ödeme Sayfasına Git' butonunu yakalar.
            WebElement checkoutBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@id=\"post-1955\"]/div/div/form/div[1]/div[2]/div/div/a")
                    )
            );
            checkoutBtn.click();
        } catch (Exception e) {
            // Eğer buton bulunamazsa (veya sepet boş kalırsa) doğrudan checkout linkine zorlar.
            driver.get("https://sivisogutma.com/checkout/");
        }
    }

    //=====================================================================================
    // THEN: Ödeme formunu doldurur, sayfayı yeniler ve verilerin temizlendiğini doğrular.
    //=====================================================================================
    @Then("47. the checkout form fields should be filled and refreshed")
    public void fill_refresh_and_verify() {
        try {
            // --- ÖDEME EKRANI KUTU YERLERİ VE DOLDURMA ---

            // 1. Ad (billing_first_name) - input alanı
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("billing_first_name"))).clear();
            driver.findElement(By.id("billing_first_name")).sendKeys("QA_Firefox");

            // 2. Soyad (billing_last_name) - input alanı
            driver.findElement(By.id("billing_last_name")).sendKeys("Tester");

            // 3. Telefon (billing_phone) - input alanı
            driver.findElement(By.id("billing_phone")).sendKeys("5550000000");

            // 4. E-posta (billing_email) - input alanı
            driver.findElement(By.id("billing_email")).sendKeys("qa_firefox@test.com");

            // 5. T.C. Kimlik No (billing_tc_kimlik) - Özel WooCommerce eklenti alanı
            driver.findElement(By.id("billing_tc_kimlik")).sendKeys("11111111111");

            // 6. Açık Adres (billing_address_1) - textarea/input alanı
            driver.findElement(By.id("billing_address_1")).sendKeys("Firefox Test Sokak No:47");

            // 7. İlçe/Şehir (billing_city) - input alanı
            driver.findElement(By.id("billing_city")).sendKeys("Çankaya");

            // 8. Posta Kodu (billing_postcode) - input alanı
            driver.findElement(By.id("billing_postcode")).sendKeys("06100");

            // --- ŞEHİR SEÇİMİ (Select2 yapısı kullanıldığı için tıklama gerektirir) ---
            // İl seçimi için Select2 dropdown konteynırına tıklar.
            WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-billing_state-container")));
            cityDropdown.click();

            // Açılan dinamik arama kutusuna 'ankara' yazar ve Enter'a basar.
            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-search__field")));
            searchField.sendKeys("ankara" + Keys.ENTER);
            Thread.sleep(1000);

            // --- KOŞULLAR VE SİPARİŞ ---
            // 'Mesafeli Satış Sözleşmesi' onay kutusunu (id="terms") bulur ve JavaScript ile tıklar.
            WebElement terms = driver.findElement(By.id("terms"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", terms);

            // 'Siparişi Onayla' (place_order) butonunu bulur ve ekranda ortalar.
            WebElement placeOrderBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("place_order")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", placeOrderBtn);

            System.out.println("Firefox DURUM: Form başarıyla dolduruldu.");

            // --- REFRESH TESTİ ---
            // Sayfa yenilendiğinde form verilerinin silinip silinmediği kontrol edilir.
            System.out.println("Firefox DURUM: Sayfa yenileniyor...");
            driver.navigate().refresh();
            Thread.sleep(4000); // Sayfanın tamamen reload olması için bekleme.

            // Yenileme sonrası 'Ad' kutusundaki veriyi çeker.
            String firstNameValue = driver.findElement(By.id("billing_first_name")).getAttribute("value");

            // EĞER değer boşsa PASS; hala veri duruyorsa FAIL verilir.
            if (firstNameValue == null || firstNameValue.isEmpty()) {
                System.out.println("PASS: Sayfa yenilendi ve form verileri temizlendi.");
            } else {
                Assert.fail("FAIL: Sayfa yenilenmesine rağmen veriler silinmedi! -> " + firstNameValue);
            }

        } catch (Exception e) {
            Assert.fail("FIREFOX TEST HATASI: " + e.getMessage());
        }
    }
}

