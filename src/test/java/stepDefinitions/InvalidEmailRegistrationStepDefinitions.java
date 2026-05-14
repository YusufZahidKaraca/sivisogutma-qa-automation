package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class InvalidEmailRegistrationStepDefinitions {

    @When("27. the user enters an invalid email address and valid password")
    public void the_user_enters_an_invalid_email_address_and_valid_password() {
        WebElement usernameInput = CommonStepDefinitions.driver.findElement(By.id("reg_username"));
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("reg_email"));
        WebElement passwordInput = CommonStepDefinitions.driver.findElement(By.id("reg_password"));

        String validUsername = "ayşe";
        String invalidEmail = "gecersizmailformati.com";
        String validPassword = "M5_pE#2kS!jV8";

        usernameInput.sendKeys(validUsername);
        emailInput.sendKeys(invalidEmail);
        passwordInput.sendKeys(validPassword);
    }

    @Then("27. it is verified that an invalid email error message is displayed")
    public void it_is_verified_that_an_invalid_email_error_message_is_displayed() {
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("reg_email"));

        // 1. Tarayıcı fark etmeksizin HTML5 validasyon mesajını JavaScript ile çekiyoruz
        JavascriptExecutor js = (JavascriptExecutor) CommonStepDefinitions.driver;
        String actualErrorMessage = (String) js.executeScript("return arguments[0].validationMessage;", emailInput);

        // Konsola yazdıralım ki hangi tarayıcıda ne aldığını debug edebilirsin
        System.out.println("Tarayıcıdan alınan hata mesajı: " + actualErrorMessage);

        // 2. DOĞRULAMA (Assertion):
        // Mesajın sadece var olup olmadığına ve boş olmadığına bakıyoruz.
        // Böylece Chrome'daki "@" kuralı ile Firefox'taki "adres yazın" kuralı arasındaki fark testi bozmaz.
        Assert.assertNotNull(actualErrorMessage, "Hata mesajı objesi null döndü!");
        Assert.assertTrue(actualErrorMessage.length() > 0, "Geçersiz mail girilmesine rağmen tarayıcı hata mesajı göstermedi!");
    }
}