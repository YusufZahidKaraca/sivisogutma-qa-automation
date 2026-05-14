package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class InvalidNameRegistrationStepDefinitions {

    @When("26. the user enters numbers in the name field and valid data in other fields")
    public void the_user_enters_numbers_in_the_name_field_and_valid_data_in_other_fields() {
        // =====================================================================
        // WOOCOMMERCE STANDART KAYIT ALANLARI
        WebElement nameInput = CommonStepDefinitions.driver.findElement(By.id("reg_username"));
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("reg_email"));
        WebElement passwordInput = CommonStepDefinitions.driver.findElement(By.id("reg_password"));
        // =====================================================================

        // DİNAMİK VERİ ÜRETİMİ: Yaklaşık 13 haneli milisaniye verisi (SADECE SAYILARDAN OLUŞUR)
        long benzersizZaman = System.currentTimeMillis();

        // Bu 13 haneli sayıyı doğrudan isim olarak veriyoruz. Hem rakam (geçersiz) hem de her saniye yepyeni!
        String dinamikGecersizIsim = String.valueOf(benzersizZaman);

        // Aynı sayıyı maili eşsiz yapmak için de kullanıyoruz
        String dinamikMail = "invalidname_" + benzersizZaman + "@sahtemail.com";

        // Dinamik verileri ve sabit şifreyi kutulara yolluyoruz
        nameInput.sendKeys(dinamikGecersizIsim);
        emailInput.sendKeys(dinamikMail);
        passwordInput.sendKeys("zL2!nX8*pQ5#m");

        // KAYIT OL BUTONUNA TIKLA (JavaScript Force Click ile)
        WebElement registerButton = CommonStepDefinitions.driver.findElement(By.name("register"));
        JavascriptExecutor js = (JavascriptExecutor) CommonStepDefinitions.driver;
        js.executeScript("arguments[0].click();", registerButton);
    }

    @Then("26. it is verified that an invalid name error message is displayed")
    public void it_is_verified_that_an_invalid_name_error_message_is_displayed() {
        // BURAYA İSİM KISMINA SAYI YAZINCA ÇIKAN HATA MESAJININ GERÇEK XPATH'İ
        WebElement errorMessage = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/div[1]/ul/li"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Beklenen hata mesajı ekranda görünmedi!");
    }
}