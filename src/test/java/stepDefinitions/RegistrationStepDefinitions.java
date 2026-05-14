package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class RegistrationStepDefinitions {

    @When("24. the user enters a valid email address and password")
    public void the_user_enters_a_valid_email_address_and_password() {
        // =====================================================================
        // SAĞ TARAFTAKİ (ÜYE OL) FORMUNUN LOCATOR'LARI
        WebElement nameInput = CommonStepDefinitions.driver.findElement(By.id("reg_username"));
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("reg_email"));
        WebElement passwordInput = CommonStepDefinitions.driver.findElement(By.id("reg_password"));
        // =====================================================================

        // DİNAMİK VERİ ÜRETİMİ: 1 Ocak 1970'ten bu yana geçen milisaniyeyi (yaklaşık 13 haneli bir sayı) alır.
        long benzersizZaman = System.currentTimeMillis();

        // Bu sayıyı isim ve mailin sonuna ekleyerek benzersizlik garantisi sağlıyoruz.
        String dinamikKullaniciAdi = "otomasyon_" + benzersizZaman;
        String dinamikMail = "test_" + benzersizZaman + "@sahtemail.com";

        // Sabit veriler yerine bu yeni dinamik değişkenleri kutulara yolluyoruz.
        nameInput.sendKeys(dinamikKullaniciAdi);
        emailInput.sendKeys(dinamikMail);
        passwordInput.sendKeys("k9#vL2!mPZ8*x"); // Şifrenin aynı kalması test açısından bir sorun yaratmaz.
    }

    @When("C the user clicks the register button")
    public void the_user_clicks_the_register_button() {
        // 1. Butonu 'XPath' ile bulalım.
        // (Aşağıdaki XPath'i kendi sistemindeki doğru XPath ile değiştirmelisin)
        WebElement registerButton = CommonStepDefinitions.driver.findElement(By.xpath("//*[@name='register']"));

        // 2. JAVASCRIPT FORCE CLICK (NİNJALARIN ZORLA TIKLAMA YÖNTEMİ)
        // Burası elementin nasıl bulunduğunu umursamaz, doğrudan tıklar.
        JavascriptExecutor js = (JavascriptExecutor) CommonStepDefinitions.driver;
        js.executeScript("arguments[0].click();", registerButton);
    }

    @Then("24. it is verified that the user is successfully registered")
    public void it_is_verified_that_the_user_is_successfully_registered() {
        // Kayıt olduktan sonra ekranda çıkan "Merhaba", "Hesabım" veya "Çıkış" yazısını arıyoruz.
        WebElement successMessage = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/div/p[1]/a"));
        Assert.assertTrue(successMessage.isDisplayed(), "Kayıt işlemi başarısız oldu!");
    }
}