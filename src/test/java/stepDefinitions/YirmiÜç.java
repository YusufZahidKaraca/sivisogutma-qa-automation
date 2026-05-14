
//////////////////////// 23-fire fox ////////////////////////

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

public class YirmiÜç {
    WebDriver driver;
    String tempEmail;

    @After("@23.")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("23. Senaryo için Firefox güvenli bir şekilde kapatıldı.");
            driver = null;
        }
    }

    //=====================================================================================
    // 1. ADIM: MAİL ÜRETİMİ
    //=====================================================================================
    @Given("23. I navigate to a temporary mail service to get an email")
    public void get_temp_email() {
        tempEmail = "bot_test_" + System.currentTimeMillis() + "@mailinator.com";
        System.out.println("Üretilen Dinamik Mail: " + tempEmail);

        // BU SATIRI EKLE (Hatalı System.setProperty satırını sil):
        io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        // Eğer Firefox standart konumdaysa alttaki satıra genelde gerek kalmaz, ama kalsın.
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
    }

    @Given("23. I navigate to {string}")
    public void i_navigate_to_generic(String url) {
        if (driver != null) driver.get(url);
    }

    @And("23. I fill the registration form with temporary email and a password")
    public void fill_registration_form() {
        driver.get("https://sivisogutma.com/hesabim/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            try {
                driver.findElement(By.id("cn-accept-cookie")).click();
            } catch (Exception e) {}

            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg_username")));// kullanıcı adı yeri "İD"
            userField.sendKeys("User_" + System.currentTimeMillis());

            driver.findElement(By.id("reg_email")).sendKeys(tempEmail);
            driver.findElement(By.id("reg_password")).sendKeys("Bot123!TestAccount"); //şife ryeri "İD"

            WebElement regButton = driver.findElement(By.xpath("//button[@name='register']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", regButton);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", regButton);

            System.out.println("Form gönderildi...");
            // Buradaki bekleme süzülme süresidir, ardından verify_blocking pusuya başlar.
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("Form hatası: " + e.getMessage());
        }
    }

    //=====================================================================================
    // 3. ADIM: FAIL DÖNDÜRME MANTIĞI (Pusu ve Baltalama)
    //=====================================================================================
    @Then("23. The registration should be blocked for temporary email")
    public void verify_blocking() {
        // WebDriverWait ile pusuya yatıyoruz. 20 saniye içinde giriş gerçekleşirse testi patlatacağız.
        WebDriverWait pusu = new WebDriverWait(driver, Duration.ofSeconds(20));
        System.out.println("Zafiyet kontrolü: Giriş belirtileri (Çıkış butonu vb.) aranıyor...");

        try {
            // PUSU: Eğer site maili kabul ederse 'Çıkış' yazısı veya 'Hesabım' paneli elbet gelecektir.
            pusu.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.linkText("Çıkış")),
                    ExpectedConditions.presenceOfElementLocated(By.partialLinkText("Hesabım")),
                    ExpectedConditions.urlContains("hesabim")
            ));

            // Eğer kod buraya ulaştıysa pusu başarılı demektir: Yani siteye girildi!
            System.err.println("!!! ZAFİYET TESPİT EDİLDİ: SİTEYE GİRİŞ YAPILDI !!!");

            // RuntimeException fırlatarak testi ve süreci kıpkırmızı bir şekilde durduruyoruz.
            throw new RuntimeException("FAIL: Site " + tempEmail + " adresini ENGELLEMEDİ! Kullanıcı içeri sızdı.");

        } catch (TimeoutException e) {
            // Eğer 20 saniye bekleyip hala giriş yapılamadıysa (mesela hata mesajı verdiyse), site güvenli demektir.
            System.out.println("PASS: Giriş engellendi. Site geçici maillere karşı korumalı.");
            System.out.println("Senaryo sonu.");
        }
    }
}

