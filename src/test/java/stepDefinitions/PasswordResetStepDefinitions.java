package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class PasswordResetStepDefinitions {

    @When("31. the user enters their registered email address {string}")
    public void the_user_enters_their_registered_email_address(String email) {
        // WooCommerce şifre sıfırlama sayfasında kutu ID'si her zaman 'user_login'dir.
        WebElement emailInput = CommonStepDefinitions.driver.findElement(By.id("user_login"));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @When("31. the user clicks the reset password button")
    public void the_user_clicks_the_reset_password_button() {
        // 'Şifreyi sıfırla' butonunu bulur ve tıklar.
        // name="wc_reset_password" WooCommerce'de standarttır.
        WebElement sendResetLinkButton = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/form/p[3]/button"));
        sendResetLinkButton.click();
    }

    @Then("31. it is verified that a password reset link sent message is displayed")
    public void it_is_verified_that_a_password_reset_link_sent_message_is_displayed() {
        // İşlem başarılıysa WooCommerce 'woocommerce-message' class'lı bir div çıkarır.
        // İçinde 'gönderildi' kelimesini arayarak doğrulamayı yapıyoruz.
        WebElement successMessage = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/div"));

        String alertText = successMessage.getText();
        Assert.assertTrue(alertText.contains("gönderildi"), "Hata: Şifre sıfırlama mesajı başarıyla görünmedi! Gelen metin: " + alertText);
    }
}