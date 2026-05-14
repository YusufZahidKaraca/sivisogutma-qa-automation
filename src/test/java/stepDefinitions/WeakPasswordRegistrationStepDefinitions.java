package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class WeakPasswordRegistrationStepDefinitions {

    @When("32. the user enters a valid email but a weak password")
    public void the_user_enters_a_valid_email_but_a_weak_password() {
        // =====================================================================
        // WOOCOMMERCE STANDART KAYIT ALANLARI
        WebElement usernameInput = CommonStepDefinitions.driver.findElement(By.id("reg_username"));
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("reg_email"));
        WebElement passwordInput = CommonStepDefinitions.driver.findElement(By.id("reg_password"));
        // =====================================================================

        long benzersizZaman = System.currentTimeMillis();
        String dinamikKullaniciAdi = "zayifsifre_" + benzersizZaman;
        String dinamikMail = "weakpass_" + benzersizZaman + "@sahtemail.com";

        usernameInput.sendKeys(dinamikKullaniciAdi);
        emailInput.sendKeys(dinamikMail);
        passwordInput.sendKeys("123");

        // 1. ADIM: Bekleme süresini 0 saniyeye çekiyoruz (Bulamazsa vakit kaybetmesin)
        CommonStepDefinitions.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        // 2. ADIM: HIZLI VE SESSİZ TIKLAMA (findElements listesi ile)
        try {
            List<WebElement> registerButtons = CommonStepDefinitions.driver.findElements(By.xpath("//*[@id=\"customer_login\"]/div[2]/form/p[4]/button"));

            // Eğer listenin boyutu 0'dan büyükse (buton sayfada varsa)
            if (registerButtons.size() > 0) {
                WebElement btn = registerButtons.get(0);
                // Buton kilitli değilse anında tıkla
                if (btn.isEnabled()) {
                    btn.click();
                }
            }
        } catch (Exception e) {
            // Tıklamaya engel olan beklenmedik bir durum olursa sessizce atlar
            System.out.println("Tıklama adımı atlandı.");
        } finally {
            // 3. ADIM: Testin geri kalanı bozulmasın diye bekleme süresini normale (örn: 10 sn) döndürüyoruz
            CommonStepDefinitions.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    @Then("32. it is verified that a weak password error message is displayed")
    public void it_is_verified_that_a_weak_password_error_message_is_displayed() {
        // DÜZELTME: className boşluk kabul etmez. Nokta ile birleştirip cssSelector kullanıyoruz.
        String arananSecici = ".woocommerce-password-strength.short";

        // Sayısal veri kontrolü: Element DOM'da var mı?
        int bulunanElementSayisi = CommonStepDefinitions.driver.findElements(By.cssSelector(arananSecici)).size();

        // 0 ise anında fail
        if (bulunanElementSayisi == 0) {
            Assert.fail("FAIL: Zayıf şifre uyarısı bulunamadı (Beklenen element sayısı 0).");
        }

        System.out.println("PASS: Zayıf şifre uyarısı görüntülendi (Element sayısı: " + bulunanElementSayisi + ").");
    }
}