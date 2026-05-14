package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UnicodeSpaceNameRegistrationStepDefinitions {

    @When("28. the user enters a unicode space in the name field and valid data in other fields")
    public void the_user_enters_a_unicode_space_in_the_name_field_and_valid_data_in_other_fields() {
        // =====================================================================
        // BURAYA KUTUCUKLARIN GERÇEK ID/XPATH'LERİNİ YAZMALISIN
        WebElement nameInput = CommonStepDefinitions.driver.findElement(By.id("reg_username"));
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("reg_email"));
        WebElement passwordInput = CommonStepDefinitions.driver.findElement(By.id("reg_password"));
        // =====================================================================

        // Görünmez Unicode boşluk karakteri (En Quad) gönderiyoruz
        String unicodeSpace = "\u2000";
        nameInput.sendKeys(unicodeSpace);

        // BURAYA GERÇEK MAİL VE ŞİFRE YAZABİLİRSİN
        emailInput.sendKeys("Galip@deneme123456.33mail.com");
        passwordInput.sendKeys("k9#vL2!mPZ8*x");

        // KAYIT OL BUTONUNA TIKLA
        WebElement registerButton = CommonStepDefinitions.driver.findElement(By.name("register"));
        registerButton.click();
    }

    @Then("28. it is verified that an invalid name or empty field error message is displayed")
    public void it_is_verified_that_an_invalid_name_or_empty_field_error_message_is_displayed() {
        // BURAYA İSİM KISMI BOŞ OLDUĞUNDA ÇIKAN HATA MESAJININ GERÇEK XPATH'İNİ YAZMALISIN
        WebElement errorMessage = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/div[1]/ul/li"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Sistem Unicode boşluk karakterini isim olarak kabul etti, hata vermedi!");
    }
}