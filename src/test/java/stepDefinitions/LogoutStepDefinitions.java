package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LogoutStepDefinitions {

    @When("25. the user clicks the logout button from the account menu")
    public void the_user_clicks_the_logout_button_from_the_account_menu() {
        // BURAYA ÇIKIŞ YAP BUTONUNUN GERÇEK XPATH'İNİ YAZMALISIN
        WebElement logoutButton = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"post-1038\"]/div/div/nav/ul/li[6]/a"));
        logoutButton.click();
    }

    @Then("25. it is verified that the user is successfully logged out")
    public void it_is_verified_that_the_user_is_successfully_logged_out() {
        // BURAYA ÇIKIŞ YAPTIKTAN SONRA TEKRAR GÖRÜNEN 'GİRİŞ YAP' LİNKİNİN/BUTONUNUN GERÇEK XPATH'İNİ YAZMALISIN
        WebElement loginLink = CommonStepDefinitions.driver.findElement(By.xpath("//*[@id=\"customer_login\"]/div[1]/form/p[3]/button"));
        Assert.assertTrue(loginLink.isDisplayed(), "Çıkış işlemi başarısız oldu!");
    }
}