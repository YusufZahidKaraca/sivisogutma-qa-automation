package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class DuplicateEmailRegistrationStepDefinitions {

    @When("33. the user enters an already registered email address and a valid password")
    public void the_user_enters_an_already_registered_email_address_and_a_valid_password() {
        // =====================================================================
        // SAĞ TARAFTAKİ (ÜYE OL) FORMUNUN STANDART ID'LERİ
        WebElement usernameInput = CommonStepDefinitions.driver.findElement(By.id("reg_username"));
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("reg_email"));
        WebElement passwordInput = CommonStepDefinitions.driver.findElement(By.id("reg_password"));
        // =====================================================================

        // ÖNEMLİ: Daha önce sisteme başarıyla kaydettiğin bir hesabı buraya yazmalısın.
        String registeredUsername = "mehmet";
        String registeredEmail = "ahmet@deneme123456.33mail.com";
        String validPassword = "sjdfl235ADsddD.!";

        // 1. Verileri doldur
        usernameInput.sendKeys(registeredUsername);
        emailInput.sendKeys(registeredEmail);
        passwordInput.sendKeys(validPassword);

        // 2. KAYIT OL BUTONUNA TIKLA (Eksik olan kahraman parçamız)
        WebElement registerButton = CommonStepDefinitions.driver.findElement(By.name("register"));
        registerButton.click();
    }

    @Then("33. it is verified that an email already in use error message is displayed")
    public void it_is_verified_that_an_email_already_in_use_error_message_is_displayed() {
        // WooCommerce'de bu hata genellikle 'woocommerce-error' listesi içinde gelir.
        WebElement errorMessage = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/div[1]/ul"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Sistem kayıtlı e-posta hatasını (Zaten kullanımda) vermedi!");
        System.out.println("Gelen Hata Mesajı: " + errorMessage.getText());
    }
}